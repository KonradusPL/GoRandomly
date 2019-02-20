package com.konradpekala.randomdestination.data.model

import android.location.Location
import com.google.type.LatLng

data class User(var fullName: String="",
                var email: String="",
                var password: String="",
                var id: String="",
                var level: Int = 0,
                var lastLocation: Location = Location("gps"),
                var destination: Location = Location("gps")

)