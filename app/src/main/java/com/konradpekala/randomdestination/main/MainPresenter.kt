package com.konradpekala.randomdestination.main

import android.Manifest
import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.konradpekala.randomdestination.data.model.Position
import com.konradpekala.randomdestination.data.model.User

import com.konradpekala.randomdestination.data.repos.MainRepository
import com.konradpekala.randomdestination.ui.base.BasePresenter


class MainPresenter<V: MainMvp.View>(view: V, val repo: MainRepository)
    : BasePresenter<V>(view), MainMvp.Presenter<V> {

    private var isNeedForGoingToUserLocation = false
    private var mUser: User? = null
    private var mLastLocation: Location? = null

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
        if (mLastLocation == null){
            view.showMessage("Upewnij się że masz włącząną lokalizacje")
            return
        }
        view.hideNewDestinationButton()
        val newDestination = repo.generateDestination(mUser!!.level,mLastLocation!!)

        cd.add(repo.updateUserDestination(mUser!!,newDestination)
            .subscribe({
                mUser!!.destination = Position(newDestination.latitude,newDestination.longitude)
                mUser!!.hasDestination = true
                view.hideSearchingSurface()
                view.showNewDestination(newDestination)
            },{t: Throwable? ->
                view.showNewDestinationButton()
            }))

    }

    override fun onMapCreated() {
        cd.add(view.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe{
                view.checkLocationSettings()
                    .subscribe {
                        cd.add(repo.observeLocation().subscribe { location: Location ->
                            mLastLocation = location

                            if (isNeedForGoingToUserLocation)
                                view.goToUserLocation(location)

                            view.showOrMoveUserLocation(location)

                            if (mUser != null && !mUser!!.hasDestination)
                                view.showOrMoveSearchingSurface(repo.getSearchingRadius(mUser!!.level),location)
                        })
                    }
            })
    }

    override fun onGoToUserLocationClick() {
        if (mLastLocation != null){
            view.goToUserLocation(mLastLocation!!)
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