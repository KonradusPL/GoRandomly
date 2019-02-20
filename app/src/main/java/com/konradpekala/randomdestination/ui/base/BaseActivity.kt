package com.konradpekala.randomdestination.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.konradpekala.randomdestination.utils.PermissionsHelper
import es.dmoral.toasty.Toasty
import io.reactivex.Completable
import io.reactivex.Observable
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


open class BaseActivity : AppCompatActivity(), MvpView {

    private val REQUEST_CHECK_SETTINGS = 1905
    private var mPermissionStatus = false


    private lateinit var mPermissionsHelper: PermissionsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPermissionsHelper = PermissionsHelper(this)

    }

    override fun hideKeyboard() {
        //all lines from StackOverflow
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showMessage(message: String) {
        val t = Toasty.info(this,message)
        t.setGravity(Gravity.BOTTOM xor Gravity.CENTER_HORIZONTAL,0,200)
        t.show()
    }

    override fun checkLocationSettings(): Completable {

        return Completable.create { emitter ->
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(LocationRequest().apply {
                    interval = 1000
                    fastestInterval = 1000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                })


            val settingsClient = LocationServices.getSettingsClient(this)
            val taskCheckSettings = settingsClient.checkLocationSettings(builder.build())


            taskCheckSettings.addOnSuccessListener{
                Log.d("Michno", "OnSuccessListener")
                emitter.onComplete()
            }

            taskCheckSettings.addOnFailureListener { exception ->
                if (exception is ResolvableApiException){
                    Log.d("Michno","OnFailureListener")
                    try {
                        exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                        doAsync {
                            while (!mPermissionStatus){
                                Thread.sleep(100)
                            }
                            uiThread {
                                emitter.onComplete()
                            }
                        }
                    } catch (sendEx: IntentSender.SendIntentException) {
                    }
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CHECK_SETTINGS){
            mPermissionStatus = true
        }
    }


    override fun checkPermission(permission: String): Observable<PermissionsHelper.PermissionState> {
        return mPermissionsHelper.checkPermission(permission)
    }

    override fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: Int) {
        ///Toasty.info(this,TextUtils.t).show()
    }

    override fun isConnectedToNetwork(): Boolean {
        return true
    }

    override fun getCtx(): Context {
        return this
    }

    override fun getActivity(): Activity {
        return this
    }
}