package com.konradpekala.randomdestination.data.model



data class User(var fullName: String="",
                var email: String="",
                var password: String="",
                var id: String="",
                var level: Int = 0,
                var hasDestination: Boolean = false,
                var lastLocation: Location = Location(0.0,0.0),
                var destination: Location = Location(0.0,0.0)

)