package com.konradpekala.randomdestination.data.repos

import com.konradpekala.randomdestination.data.FirebaseDatabase
import com.konradpekala.randomdestination.data.auth.FirebaseAuth
import com.konradpekala.randomdestination.data.prefs.SharedPrefs
import com.konradpekala.randomdestination.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single

class LoginRepository(private val mAuth: FirebaseAuth,private val mDb: FirebaseDatabase) {

    fun signUp(email: String, password: String): Single<String> {
        return mAuth.signUp(email,password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun signIn(email: String, password: String): Completable{
        return mAuth.signIn(email,password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun createUser(email: String,password: String,id: String): Single<String>{
        return mDb.createUser(email, password, id)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}