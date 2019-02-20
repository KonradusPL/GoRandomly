package com.konradpekala.randomdestination.data.prefs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konradpekala.randomdestination.data.auth.FirebaseAuth
import com.konradpekala.randomdestination.main.MainActivity
import com.konradpekala.randomdestination.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth().isUserLoggedIn()){
            startActivity(Intent(this,MainActivity::class.java))
        }else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
