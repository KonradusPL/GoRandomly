package com.konradpekala.randomdestination.utils


import android.graphics.Color
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.konradpekala.randomdestination.R
import com.konradpekala.randomdestination.main.MainMvp


class MapHelper(val mView: MainMvp.View, fragment: Fragment?) : OnMapReadyCallback,MainMvp.MapInterface {

    private var mMap: GoogleMap? = null

    private var mMarkerUser: Marker? = null
    private var mSearchingCircle: Circle? = null
    private var mDestinationMarker:Marker? = null


    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("map","ready")
        mMap = googleMap

        mView.getPresenter().onMapCreated()

        /*mMap?.apply {
            setPadding(0,60,0,0)
            isBuildingsEnabled = false
            uiSettings.isTiltGesturesEnabled = false
            setMinZoomPreference(19f)
            setMaxZoomPreference(19f)
        }

        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(52.208856, 21.008713))
            .zoom(19f)
            .build()
        mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),1,null)

        try {
            mMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                mvpView.getCtx(), R.raw.theme_map))
        }catch (e: Resources.NotFoundException) {
            Log.e("myErrors", "Can't find style. Error: ", e)
        }
*/
    }

    override fun goToUserLocation(location: Location){
        if (mMap == null)return

        val builder = CameraPosition.Builder()
            .target(LatLng(location.latitude, location.longitude))

        if(mMap!!.cameraPosition.zoom < 15f)
            builder.zoom(18f)
        else
            builder.zoom(mMap!!.cameraPosition.zoom)

        mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()),1000,null)
    }

    override fun showOrMoveSearchingSurface(radius: Int,l: Location){
        if (mMap == null)return

        val cPrimaryDark = ContextCompat.getColor(mView.getCtx(), R.color.colorPrimaryDark)

        if (mSearchingCircle == null || (mSearchingCircle!= null && mSearchingCircle!!.radius != radius.toDouble())){
            mSearchingCircle = mMap!!.addCircle(CircleOptions().center(LatLng(l.latitude,l.longitude))
                .radius(radius.toDouble())
                .fillColor(0x55008577)
                .strokeColor(cPrimaryDark)
                .strokeWidth(3f))
        }else{
            MapUtils.animateCircleToGB(mSearchingCircle!!, LatLng(l.latitude,l.longitude), LatLngUtils)
        }
    }

    override fun hideSearchingSurface() {
        mSearchingCircle?.isVisible = false
    }


    override fun showNewDestination(location: LatLng){
        if (mMap == null)return

        if (mDestinationMarker == null){
            mDestinationMarker = mMap!!.addMarker(MarkerOptions()
                .position(LatLng(location.latitude, location.longitude)))
        }else{
            mDestinationMarker?.isVisible = true
            mDestinationMarker?.position = location
        }

    }

    override fun hideDestination() {
        mDestinationMarker?.isVisible = false
    }

    override fun showOrMoveUserLocation(location: Location){
        if (mMap == null)return

        if (mMarkerUser == null){
            mMarkerUser = mMap!!.addMarker(MarkerOptions()
                .position(LatLng(location.latitude, location.longitude)))
        }else{
            MapUtils.animateMarkerToGB(mMarkerUser!!, LatLng(location.latitude,location.longitude),
                LatLngUtils
            )
        }

    }

}