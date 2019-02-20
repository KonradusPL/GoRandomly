package com.konradpekala.randomdestination.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.konradpekala.randomdestination.R
import com.konradpekala.randomdestination.data.auth.FirebaseAuth
import com.konradpekala.randomdestination.data.repos.LoginRepository
import com.konradpekala.randomdestination.main.MainActivity
import com.konradpekala.randomdestination.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(),LoginMvp.View {

    private val mPresenter = LoginPresenter<LoginMvp.View>(this, LoginRepository(FirebaseAuth))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initButtons()
    }

    private fun initButtons(){
        buttonSignUp.setOnClickListener {
            mPresenter.onSignUpButtonClick(fieldEmail.text.toString().trim(),fieldPassword.text.toString().trim())
        }
        buttonSignIn.setOnClickListener {
            mPresenter.onSignInButtonClick(fieldEmail.text.toString().trim(),fieldPassword.text.toString().trim())
        }
    }

    override fun showLoading() {
        progressBarLogin.visibility = View.VISIBLE
        fieldEmail.isEnabled = false
        fieldPassword.isEnabled = false
    }

    override fun hideLoading() {
        progressBarLogin.visibility = View.GONE
        fieldEmail.isEnabled = true
        fieldPassword.isEnabled = true
    }

    override fun openMainActivity() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}
