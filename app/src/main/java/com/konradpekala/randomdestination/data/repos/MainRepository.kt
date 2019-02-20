package com.konradpekala.randomdestination.data.repos

import android.location.Location
import com.konradpekala.randomdestination.data.LocationProvider
import com.konradpekala.randomdestination.utils.SchedulerProvider
import io.reactivex.Observable

class MainRepository(private val mLocationProvider: LocationProvider) {

    var lastLocation : Location? = null

    fun observeLocation(): Observable<Location>{
        return mLocationProvider.observeLocation()
            .doOnNext {location: Location? ->  lastLocation = location }
            .subscribeOn(SchedulerProvider.ui())
            .observeOn(SchedulerProvider.ui())
    }

    fun stopObservingLocation(){
        mLocationProvider.stopObservingLocation()
    }
}