package com.konradpekala.randomdestination.data.prefs

import android.content.Context
import androidx.core.content.edit
import com.konradpekala.randomdestination.Preferences

class SharedPrefs(context: Context): Preferences {

    private val USER_FULLNAME = "CURRENT_USER_FULLNAME"
    private val USER_PASSWORD = "CURRENT_USER_FULLNAME"
    private val USER_EMAIL = "CURRENT_USER_FULLNAME"
    private val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"


    private val mSharedPrefs = context.getSharedPreferences("shared_preferences",Context.MODE_PRIVATE)

    override fun isUserLoggedIn(): Boolean {
        return mSharedPrefs.getBoolean(IS_USER_LOGGED_IN,false)
    }

    override fun setIsUserLogged(value: Boolean) {
        mSharedPrefs.edit {
            putBoolean(IS_USER_LOGGED_IN,value)
        }
    }
}