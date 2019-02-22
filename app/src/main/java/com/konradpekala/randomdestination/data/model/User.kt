package com.konradpekala.randomdestination.data.model



data class User(var fullName: String="",
                var email: String="",
                var password: String="",
                var id: String="",
                var level: Int = 1,
                var hasDestination: Boolean = false,
                var lastPosition: Position = Position(0.0,0.0),
                var destination: Position = Position(0.0,0.0)

)