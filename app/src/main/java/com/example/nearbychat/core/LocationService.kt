package com.example.nearbychat.core

import android.content.Context
import android.location.Location
import com.amap.api.location.*

class LocationService(context: Context) {
    private var locationClient: AMapLocationClient = AMapLocationClient(context)
    private var locationOption: AMapLocationClientOption = AMapLocationClientOption()

    interface LocationListener {
        fun onLocationUpdated(location: Location)
    }

    private var listener: LocationListener? = null

    fun setLocationListener(listener: LocationListener) {
        this.listener = listener
    }

    fun startLocationUpdates() {
        locationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        locationClient.setLocationOption(locationOption)

        locationClient.setLocationListener { location ->
            if (location.errorCode == 0) {
                val loc = Location("Gaode").apply {
                    latitude = location.latitude
                    longitude = location.longitude
                }
                listener?.onLocationUpdated(loc)
            }
        }
        locationClient.startLocation()
    }

    fun stopLocationUpdates() {
        locationClient.stopLocation()
    }
}
