package com.konradpekala.randomdestination.data.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable

object FirebaseAuth {
    fun isUserLoggedIn():Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    fun signUp(email: String, password: String): Completable{
        return Completable.create { emitter ->
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.w("createUserWithEmail:s", task.exception)
                        emitter.onComplete()
                    }else{
                        Log.w("createUserWithEmail:e", task.exception)
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    fun signIn(email: String, password: String): Completable{
        return Completable.create { emitter ->
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.w("createUserWithEmail:s", task.exception)
                        emitter.onComplete()
                    }else{
                        Log.w("createUserWithEmail:e", task.exception)
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }
}