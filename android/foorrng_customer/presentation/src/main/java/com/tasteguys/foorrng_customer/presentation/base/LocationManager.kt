package com.tasteguys.foorrng_customer.presentation.base

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.naver.maps.geometry.LatLng

class LocationManager(private val context: Context) {
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    private val locationProvider = LocationManager.GPS_PROVIDER
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): LatLng{
        val userLocation: Location = locationManager?.getLastKnownLocation(locationProvider)!!
        return LatLng(userLocation.latitude, userLocation.longitude)
    }
}