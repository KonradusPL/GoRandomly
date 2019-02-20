package com.konradpekala.randomdestination.data

import com.google.firebase.firestore.FirebaseFirestore
import com.konradpekala.randomdestination.data.model.User
import io.reactivex.Completable
import io.reactivex.Single


class FirebaseDatabase {
    val db = FirebaseFirestore.getInstance()

    fun createUser(email: String,password: String,id: String): Single<String>{
        val user = User(email = email,password = password,id = id)
        return Single.create { emitter ->
            db.collection("users").document(user.id).set(user)
                .addOnSuccessListener { emitter.onSuccess("success") }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }
}