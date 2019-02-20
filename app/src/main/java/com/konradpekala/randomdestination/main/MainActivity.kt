package com.konradpekala.randomdestination.main

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.SupportMapFragment
import com.konradpekala.randomdestination.R
import com.konradpekala.randomdestination.data.LocationProvider
import com.konradpekala.randomdestination.data.auth.FirebaseAuth
import com.konradpekala.randomdestination.data.repos.LoginRepository
import com.konradpekala.randomdestination.data.repos.MainRepository
import com.konradpekala.randomdestination.ui.base.BaseActivity
import com.konradpekala.randomdestination.utils.MapHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),MainMvp.View {

    private lateinit var mFragmentMap: SupportMapFragment
    private lateinit var mMapHelper: MapHelper

    private lateinit var mPresenter :MainPresenter<MainMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this, MainRepository(LocationProvider(this)))

        initMapStuff()
        initButtons()

        mPresenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    private fun initButtons(){
        buttonGoToUserLocation.setOnClickListener {
            mPresenter.onGoToUserLocationClick()
        }
    }

    private fun initMapStuff(){
        mFragmentMap = SupportMapFragment.newInstance()

        mMapHelper = MapHelper(this,null)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mapContainer,mFragmentMap as Fragment)
            .commit()

        mFragmentMap.getMapAsync(mMapHelper)
    }

    override fun goToUserLocation(location: Location) {
        mMapHelper.goToUserLocation(location)
    }
}
