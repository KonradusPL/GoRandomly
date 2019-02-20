package com.konradpekala.randomdestination.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.SupportMapFragment
import com.konradpekala.randomdestination.R
import com.konradpekala.randomdestination.ui.base.BaseActivity
import com.konradpekala.randomdestination.ui.base.MvpView
import com.konradpekala.randomdestination.utils.MapHelper

class MainActivity : BaseActivity(),MvpView {

    private lateinit var fragmentMap: SupportMapFragment
    private lateinit var mMapHelper: MapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentMap = SupportMapFragment.newInstance()

        mMapHelper = MapHelper(this,null)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mapContainer,fragmentMap as Fragment)
            .commit()

        fragmentMap.getMapAsync(mMapHelper)
    }
}
