package com.example.nearbychat.service

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

class LocationService(context: Context, private val callback: (location: com.amap.api.location.AMapLocation) -> Unit) {
    private val locationClient = AMapLocationClient(context)

    fun startLocationUpdates() {
        val option = AMapLocationClientOption()
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        option.isOnceLocation = false
        locationClient.setLocationOption(option)
        locationClient.setLocationListener(callback)
        locationClient.startLocation()
    }

    fun stopLocationUpdates() {
        locationClient.stopLocation()
    }
}
