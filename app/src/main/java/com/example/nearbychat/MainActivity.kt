package com.example.nearbychat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.amap.api.location.AMapLocationClient
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.*
import com.example.nearbychat.data.FriendRepository
import com.example.nearbychat.model.Zhouqiong
import com.example.nearbychat.service.LocationService
import com.example.nearbychat.ui.LoginActivity
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var aMap: AMap
    private lateinit var locationService: LocationService
    private val friendRepository = FriendRepository()
    private val markers = mutableMapOf<String, Marker>()
    private var currentUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // **检查是否已登录，防止闪退**
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        currentUser = sharedPreferences.getString("logged_in_user", null)

        if (currentUser == null) {
            navigateToLogin()
            return
        }

        setContentView(R.layout.activity_main)
        AMapLocationClient.updatePrivacyShow(this, true, true)
        AMapLocationClient.updatePrivacyAgree(this, true)
        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)

        try {
            aMap = mapView.map
            setupMap()
            checkPermissionsAndStart()
        } catch (e: Exception) {
            Toast.makeText(this, "地图初始化失败", Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupMap() {
        aMap.uiSettings.apply {
            isZoomControlsEnabled = false
            isCompassEnabled = true
            isMyLocationButtonEnabled = true
        }
        aMap.isMyLocationEnabled = true
    }

    private fun checkPermissionsAndStart() {
        if (hasPermissions()) {
            startLocationUpdates()
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun hasPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.all { it.value }) {
            startLocationUpdates()
        } else {
            Toast.makeText(this, "未授予必要权限，无法使用地图", Toast.LENGTH_LONG).show()
        }
    }

    private fun startLocationUpdates() {
        try {
            locationService = LocationService(this) { location ->
                val latLng = LatLng(location.latitude, location.longitude)
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                // 更新好友位置
                updateFriendMarkers()
            }
            locationService.startLocationUpdates()
        } catch (e: Exception) {
            Toast.makeText(this, "定位服务异常", Toast.LENGTH_SHORT).show()
        }
    }

    /***  🚀 加载好友并在地图上显示头像或红点  ***/
    private fun updateFriendMarkers() {
        val friends = friendRepository.getNearbyFriends()

        for (friend in friends) {
            val position = LatLng(friend.latitude, friend.longitude)

            if (markers.containsKey(friend.id)) {
                // 更新现有的 Marker 位置
                markers[friend.id]?.position = position
            } else {
                val markerOptions = MarkerOptions()
                    .position(position)
                    .anchor(0.5f, 0.5f) // 让 marker 居中对齐

                if (friend.avatarUrl != null) {
                    loadAvatarMarker(friend.id, friend.avatarUrl!!, markerOptions)
                } else {
                    // 显示红点
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    markers[friend.id] = aMap.addMarker(markerOptions)
                }
            }
        }
    }


    /*** 🌟 异步加载头像 ***/
    private fun loadAvatarMarker(friendId: String, avatarUrl: String, markerOptions: MarkerOptions) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val bitmap = getBitmapFromURL(avatarUrl)
                runOnUiThread {
                    if (bitmap != null) {
                        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(scaledBitmap))
                        markers[friendId] = aMap.addMarker(markerOptions)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /*** 📷 从 URL 加载图片 ***/
    private fun getBitmapFromURL(url: String): Bitmap? {
        return try {
            val inputStream = URL(url).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        if (::mapView.isInitialized && mapView.parent != null) {
            mapView.onDestroy()
        }
        super.onDestroy()
    }
}
