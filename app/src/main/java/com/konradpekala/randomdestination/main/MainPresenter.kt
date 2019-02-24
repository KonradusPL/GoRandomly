package com.konradpekala.randomdestination.main

import android.Manifest
import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.konradpekala.randomdestination.R
import com.konradpekala.randomdestination.data.model.Position
import com.konradpekala.randomdestination.data.model.User

import com.konradpekala.randomdestination.data.repos.MainRepository
import com.konradpekala.randomdestination.ui.base.BasePresenter
import com.konradpekala.randomdestination.utils.LatLngUtils


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
                view.updateNameText(user.fullName)
                view.updateLevelText(user.level)
                if (!mUser!!.hasDestination){
                    view.showNewDestinationButton()
                }else{
                    val pos = mUser!!.destination
                    view.getMap().showNewDestination(LatLng(pos.lat,pos.lng))
                }
            },{t: Throwable? ->
                Log.d("getUser",t.toString())
            }))
    }

    override fun onNewDestinationButtonClick() {
        if (mLastLocation == null){
            view.showMessage(R.string.location_check)
            return
        }
        view.hideNewDestinationButton()
        val newDestination = repo.generateDestination(mUser!!.level,mLastLocation!!)

        cd.add(repo.updateUserDestination(mUser!!,newDestination)
            .subscribe({t:User ->
                mUser = t
                view.getMap().hideSearchingSurface()
                view.getMap().showNewDestination(newDestination)
            },{t: Throwable? ->
                view.showNewDestinationButton()
            }))

    }

    override fun onMapCreated() {
        var userIsUpdatedOnReach = false
        cd.add(view.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .flatMap { t: String -> view.checkLocationSettings() }
            .subscribe({
                cd.add(repo.observeLocation().subscribe { location: Location ->
                    mLastLocation = location

                    if (isNeedForGoingToUserLocation){
                        isNeedForGoingToUserLocation = false
                        view.getMap().goToUserLocation(location)
                    }

                    view.getMap().showOrMoveUserLocation(location)

                    if (mUser != null && !mUser!!.hasDestination)
                        view.getMap().showOrMoveSearchingSurface(repo.getSearchingRadius(mUser!!.level),location)

                    if (mUser != null && mUser!!.hasDestination){

                        val distance = repo.getDistance(mLastLocation!!,mUser!!.destination)
                        view.updateDistanceText(distance)

                        if(repo.hasReachedDestination(distance) && !userIsUpdatedOnReach){
                            userIsUpdatedOnReach = true
                            cd.add(repo.updateUserOnReachDestination(mUser!!)
                                .subscribe({t:User ->
                                    userIsUpdatedOnReach = false
                                    mUser = t
                                    view.hideDistanceText()
                                    view.updateLevelText(t.level)
                                    view.getMap().hideDestination()
                                    view.showReachedDestinationDialog()
                                    view.showNewDestinationButton()
                                    view.getMap().showOrMoveSearchingSurface(
                                        repo.getSearchingRadius(mUser!!.level),mLastLocation!!)
                                },{t: Throwable? ->
                                    userIsUpdatedOnReach = false
                                }))
                        }
                    }
                })
            }, { t: Throwable? ->

            }))
    }

    override fun onLogOutClick() {
        repo.logOut()
        view.openLoginActivity()
    }

    override fun onChangeNameClick(newName: String) {
        if (mUser == null)
            return
        cd.add(repo.changeName(newName,mUser!!).subscribe({
            view.updateNameText(newName)
        },{t: Throwable? ->
            view.showMessage(R.string.change_name_error)
        }))
    }
    override fun onGoToUserLocationClick() {
        if (mLastLocation != null){
            view.getMap().goToUserLocation(mLastLocation!!)
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