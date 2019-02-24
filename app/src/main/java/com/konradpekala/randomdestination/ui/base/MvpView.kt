package com.konradpekala.randomdestination.ui.base

import android.app.Activity
import android.content.Context
import com.konradpekala.randomdestination.utils.PermissionsHelper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MvpView {

    fun hideKeyboard()

    fun showMessage(message: String)
    fun showMessage(message: Int)

    fun isConnectedToNetwork(): Boolean

    fun getCtx(): Context
    fun getActivity(): Activity

    fun checkPermission(permission: String): Single<String>
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int)

    fun checkLocationSettings(): Single<String>
    fun openLink(link: String)
}