package com.konradpekala.randomdestination.data.repos

import com.konradpekala.randomdestination.data.auth.FirebaseAuth
import com.konradpekala.randomdestination.data.prefs.SharedPrefs
import com.konradpekala.randomdestination.utils.SchedulerProvider
import io.reactivex.Completable

class LoginRepository(private val mAuth: FirebaseAuth) {

    fun signUp(email: String, password: String): Completable{
        return mAuth.signUp(email,password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun signIn(email: String, password: String): Completable{
        return mAuth.signIn(email,password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}