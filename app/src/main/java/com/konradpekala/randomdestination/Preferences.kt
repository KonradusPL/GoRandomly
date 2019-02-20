package com.konradpekala.randomdestination

interface Preferences {
    fun isUserLoggedIn(): Boolean
    fun setIsUserLogged(value:Boolean)
}