package com.konradpekala.randomdestination.data.repos

import android.location.Location
import com.konradpekala.randomdestination.data.FirebaseDatabase
import com.konradpekala.randomdestination.data.LocationProvider
import com.konradpekala.randomdestination.data.auth.FirebaseAuth
import com.konradpekala.randomdestination.data.model.User
import com.konradpekala.randomdestination.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class MainRepository(private val mLocationProvider: LocationProvider,
                     private val mDb: FirebaseDatabase,
                     private val mAuth: FirebaseAuth) {

    var lastLocation : Location? = null

    private var mUser: User? = null

    fun observeLocation(): Observable<Location>{
        return mLocationProvider.observeLocation()
            .doOnNext {location: Location? ->  lastLocation = location }
            .subscribeOn(SchedulerProvider.ui())
            .observeOn(SchedulerProvider.ui())
    }

    fun getUser(): Single<User>{
        val userId = mAuth.getUserId()
        return if (userId == "")
            Single.error(Throwable("EmptyUserId"))
        else
            mDb.getUser(userId)
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSuccess { user: User? ->
                    mUser = user
                }
    }

    /*fun generateDestination(): Completable{
        if (mUser == null)
            return Completable.error(Throwable("null"))
        mUser!!.level

    }*/

    fun getSearchingRadius(): Int{
        return (mUser!!.level+1)*10
    }

    fun stopObservingLocation(){
        mLocationProvider.stopObservingLocation()
    }
}