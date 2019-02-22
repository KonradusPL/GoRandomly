package com.konradpekala.randomdestination.data.repos

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.konradpekala.randomdestination.data.FirebaseDatabase
import com.konradpekala.randomdestination.data.LocationProvider
import com.konradpekala.randomdestination.data.auth.FirebaseAuth
import com.konradpekala.randomdestination.data.model.Position
import com.konradpekala.randomdestination.data.model.User
import com.konradpekala.randomdestination.utils.LatLngUtils
import com.konradpekala.randomdestination.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class MainRepository(private val mLocationProvider: LocationProvider,
                     private val mDb: FirebaseDatabase,
                     private val mAuth: FirebaseAuth) {

    fun observeLocation(): Observable<Location>{
        return mLocationProvider.observeLocation()
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
    }

    fun updateUserDestination(user: User, newDestination: LatLng): Single<User>{
        val userCopy = user.copy()

        userCopy.hasDestination = true
        userCopy.destination = Position(newDestination.latitude,newDestination.longitude)

        return mDb.updateUser(userCopy)
            .toSingle { userCopy }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun updateUserOnReachDestination(user: User): Single<User>{
        val userCopy = user.copy()
        userCopy.level++
        userCopy.hasDestination = false
        userCopy.destination = Position()

        return mDb.updateUser(userCopy)
            .toSingle { userCopy }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun logOut(){
        mAuth.logOut()
    }

    fun changeName(name: String, user: User): Completable{
        val userCopy = user.copy()
        userCopy.fullName = name
        return mDb.updateUser(userCopy)
    }

    fun generateDestination(level: Int, lastLocation: Location): LatLng{

        val radius = (level)*(level)*10

        val l = LatLng(lastLocation.latitude,lastLocation.longitude)

        Log.d("generateDestination","lat${l.latitude} lng${l.longitude}")

        val randomPosition = LatLngUtils.getRandomLocation(l,radius)

        return randomPosition
    }

    fun getDistance(lastLocation: Location, dest: Position): Float{
        return LatLngUtils.getDistanceInMeters(
            LatLng(lastLocation.latitude,lastLocation.longitude),
            LatLng(dest.lat,dest.lng))
    }

    fun hasReachedDestination(distance: Float) = distance < 40

    fun getSearchingRadius(level: Int): Int{
        return (level)*(level)*10
    }

    fun stopObservingLocation(){
        mLocationProvider.stopObservingLocation()
    }
}