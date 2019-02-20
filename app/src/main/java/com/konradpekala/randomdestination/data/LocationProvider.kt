package com.konradpekala.randomdestination.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable

class LocationProvider(private val context: Context){

    private var millis = 1000L

    private var locationRequest =LocationRequest().apply {
        interval = millis
        fastestInterval = millis
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private var locationCallback: LocationCallback? = null
    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    var lastLocation = Location("GPS")

    @SuppressLint("MissingPermission")
    fun observeLocation(): Observable<Location>{
        return Observable.create { emitter ->
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        lastLocation.set(location)
                        emitter.onNext(location)
                    }
                }
            }

            locationClient.requestLocationUpdates(locationRequest,
                locationCallback,null)
        }

    }


    fun stopObservingLocation(){
        locationClient.removeLocationUpdates(locationCallback)
    }
}