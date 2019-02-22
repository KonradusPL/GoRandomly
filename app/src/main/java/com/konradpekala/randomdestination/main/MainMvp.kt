package com.konradpekala.randomdestination.main

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.konradpekala.randomdestination.ui.base.MvpPresenter
import com.konradpekala.randomdestination.ui.base.MvpView

interface MainMvp {
    interface View: MvpView{
        fun goToUserLocation(location: Location)
        fun showOrMoveUserLocation(location: Location)
        fun showOrMoveSearchingSurface(radius: Int,location: Location)
        fun showNewDestination(location: LatLng)
        fun hideSearchingSurface()
        fun showNewDestinationButton()
        fun hideNewDestinationButton()
        fun getPresenter(): Presenter<View>
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onGoToUserLocationClick()
        fun onMapCreated()
        fun onNewDestinationButtonClick()
    }
}