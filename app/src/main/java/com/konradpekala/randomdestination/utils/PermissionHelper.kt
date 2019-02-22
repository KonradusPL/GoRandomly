package com.konradpekala.randomdestination.utils

import android.app.Activity
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.reactivex.Observable
import io.reactivex.Single

class PermissionsHelper(val context: Activity) {
    enum class PermissionState{
        GRANTED,
        RATIONALE_SHOULD_BE_SHOWN,
        DENIED
    }

    fun checkPermission(permission: String): Single<String> {

        return Single.create {
                emitter ->
            val permissionListener = object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Log.d("permission","GRANTED")
                    emitter.onSuccess("GRANTED")
                }
                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    Log.d("permission","RATIONALE_SHOULD_BE_SHOWN")
                    emitter.onSuccess("RATIONALE_SHOULD_BE_SHOWN")
                    token?.continuePermissionRequest()
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Log.d("permission","DENIED")
                    emitter.onError(Throwable("DENIED"))
                }

            }
            Dexter.withActivity(context)
                .withPermission(permission)
                .withListener(permissionListener)
                .check()

        }
    }
}