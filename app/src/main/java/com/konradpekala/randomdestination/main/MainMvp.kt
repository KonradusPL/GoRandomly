package com.konradpekala.randomdestination.main

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.konradpekala.randomdestination.ui.base.MvpPresenter
import com.konradpekala.randomdestination.ui.base.MvpView

interface MainMvp {
    interface View: MvpView{
        fun showNewDestinationButton()
        fun hideNewDestinationButton()
        fun updateDistanceText(distance: Float)
        fun hideDistanceText()
        fun showDistanceText()
        fun getPresenter(): Presenter<View>
        fun getMap(): MapInterface
        fun updateNameText(name: String)
        fun openLoginActivity()
        fun updateLevelText(level: Int)
        fun showReachedDestinationDialog()
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onGoToUserLocationClick()
        fun onMapCreated()
        fun onNewDestinationButtonClick()
        fun onLogOutClick()
        fun onChangeNameClick(newName: String)
    }
    interface MapInterface{
        fun goToUserLocation(location: Location)
        fun showOrMoveUserLocation(location: Location)
        fun showOrMoveSearchingSurface(radius: Int,l: Location)
        fun showNewDestination(location: LatLng)
        fun hideDestination()
        fun hideSearchingSurface()
    }
}