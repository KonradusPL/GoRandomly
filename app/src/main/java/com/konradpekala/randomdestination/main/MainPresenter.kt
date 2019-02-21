package com.konradpekala.randomdestination.main

import android.Manifest
import android.location.Location
import android.util.Log
import com.konradpekala.randomdestination.data.model.User

import com.konradpekala.randomdestination.data.repos.MainRepository
import com.konradpekala.randomdestination.ui.base.BasePresenter


class MainPresenter<V: MainMvp.View>(view: V, val repo: MainRepository)
    : BasePresenter<V>(view), MainMvp.Presenter<V> {

    private var isNeedForGoingToUserLocation = false
    private var mUser: User? = null

    override fun onCreate() {
        super.onCreate()

        cd.add(repo.getUser()
            .subscribe({user: User ->
                mUser = user
                if (!mUser!!.hasDestination){
                    view.showNewDestinationButton()
                }
            },{t: Throwable? ->
                Log.d("getUser",t.toString())
            }))
    }

    override fun onNewDestinationButtonClick() {

    }

    override fun onMapCreated() {
        cd.add(view.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe{
                view.checkLocationSettings()
                    .subscribe {
                        cd.add(repo.observeLocation().subscribe { location: Location? ->
                            if (isNeedForGoingToUserLocation && location != null)
                                view.goToUserLocation(location)
                            view.showOrMoveUserLocation(location!!)
                            if (mUser != null && !mUser!!.hasDestination)
                                view.showOrMoveSearchingSurface(repo.getSearchingRadius(),location)
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