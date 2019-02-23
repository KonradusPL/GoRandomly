package com.konradpekala.randomdestination.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.gms.maps.SupportMapFragment
import com.konradpekala.randomdestination.R
import com.konradpekala.randomdestination.data.FirebaseDatabase
import com.konradpekala.randomdestination.data.LocationProvider
import com.konradpekala.randomdestination.data.auth.FirebaseAuth
import com.konradpekala.randomdestination.data.repos.MainRepository
import com.konradpekala.randomdestination.ui.base.BaseActivity
import com.konradpekala.randomdestination.ui.login.LoginActivity
import com.konradpekala.randomdestination.utils.MapHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_change_name.view.*


class MainActivity : BaseActivity(),MainMvp.View {

    private lateinit var mFragmentMap: SupportMapFragment
    private lateinit var mMapHelper: MapHelper

    private lateinit var mPresenter :MainPresenter<MainMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this, MainRepository(LocationProvider(this), FirebaseDatabase(), FirebaseAuth()))

        initMapStuff()
        initButtons()

        mPresenter.onCreate()
    }

    fun animate(first: Boolean){
        val constraintSet1 = ConstraintSet()
        constraintSet1.clone(this, R.layout.activity_main)

        val transition = Slide(Gravity.BOTTOM)

        TransitionManager.beginDelayedTransition(constraintLayout,transition)
        layoutProfile.visibility = if(first) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    private fun initButtons(){
        buttonGoToUserLocation.setOnClickListener {
            mPresenter.onGoToUserLocationClick()
        }
        buttonNewDestination.setOnClickListener {
            mPresenter.onNewDestinationButtonClick()
        }
        imageGoToProfile.setOnClickListener {
            animate(true)
        }
        iconExitFromProfile.setOnClickListener {
            animate(false)
        }
        buttonLogOut.setOnClickListener {
            mPresenter.onLogOutClick()
        }
        buttonChangeName.setOnClickListener {
            createChangeNameDialog()
        }

    }

    private fun createChangeNameDialog(){
        val customView = LayoutInflater.from(this).inflate(R.layout.dialog_change_name,constraintLayout,false)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Zmiana nazwy")
            .setView(customView).create()

        customView.buttonDialogCreate.setOnClickListener {
            mPresenter.onChangeNameClick(customView.fieldUserName.text.toString())
            dialog.hide()
        }

        dialog.show()

    }

    private fun initMapStuff(){
        mFragmentMap = SupportMapFragment.newInstance()

        mMapHelper = MapHelper(this,null)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mapContainer,mFragmentMap as Fragment)
            .commit()

        mFragmentMap.getMapAsync(mMapHelper)
    }

    override fun onBackPressed() {
        if(layoutProfile.visibility == View.VISIBLE)
            animate(false)
        else
            super.onBackPressed()
    }

    override fun getPresenter() = mPresenter

    override fun getMap() = mMapHelper

    override fun showNewDestinationButton() {
        buttonNewDestination.visibility = View.VISIBLE
    }

    override fun updateNameText(name: String) {
        textUserName.text = name
    }

    override fun updateDistanceText(distance: Float) {
        if (textDistance.visibility == View.GONE)
            textDistance.visibility = View.VISIBLE
        textDistance.text = "Dystans: ${distance.toInt()}m"
    }

    override fun openLoginActivity() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    override fun hideNewDestinationButton() {
        buttonNewDestination.visibility = View.GONE
    }

    override fun showDistanceText() {
        textDistance.visibility = View.VISIBLE
    }
    override fun hideDistanceText() {
        textDistance.visibility = View.GONE
    }
}
