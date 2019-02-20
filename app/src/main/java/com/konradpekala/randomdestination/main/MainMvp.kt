package com.konradpekala.randomdestination.main

import android.location.Location
import com.konradpekala.randomdestination.ui.base.MvpPresenter
import com.konradpekala.randomdestination.ui.base.MvpView

interface MainMvp {
    interface View: MvpView{
        fun goToUserLocation(location: Location)
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onGoToUserLocationClick()
    }
}