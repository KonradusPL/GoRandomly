package com.konradpekala.randomdestination.utils


import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.konradpekala.randomdestination.ui.base.MvpView


class MapHelper(val mvpView: MvpView, fragment: Fragment?) : OnMapReadyCallback {

    private var mMap: GoogleMap? = null


    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("map","ready")
        mMap = googleMap

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

}