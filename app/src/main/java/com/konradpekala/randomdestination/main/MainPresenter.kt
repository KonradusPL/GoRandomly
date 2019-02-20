package com.konradpekala.randomdestination.main

import android.Manifest
import android.location.Location

import com.konradpekala.randomdestination.data.repos.MainRepository
import com.konradpekala.randomdestination.ui.base.BasePresenter


class MainPresenter<V: MainMvp.View>(view: V, val repo: MainRepository)
    : BasePresenter<V>(view), MainMvp.Presenter<V> {

    private var isNeedForGoingToUserLocation = false

    override fun onCreate() {
        super.onCreate()

        cd.add(view.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe{
                view.showMessage("checkPermission")
                view.checkLocationSettings()
                    .subscribe {
                        view.showMessage("checkLocationSettings")
                        cd.add(repo.observeLocation().subscribe { location: Location? ->
                            if (isNeedForGoingToUserLocation && location != null)
                                view.goToUserLocation(location)
                        })
                    }
            })
    }

    override fun onGoToUserLocationClick() {
        if (repo.lastLocation != null){
            view.goToUserLocation(repo.lastLocation!!)
        }
        else{
            isNeedForGoingToUserLocation = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        repo.stopObservingLocation()
    }
}