package com.konradpekala.randomdestination.ui.login

import com.konradpekala.randomdestination.ui.base.MvpPresenter
import com.konradpekala.randomdestination.ui.base.MvpView

interface LoginMvp {
    interface View: MvpView{
        fun showLoading()
        fun hideLoading()
        fun openMainActivity()
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onSignUpButtonClick(email: String, password: String)
        fun onSignInButtonClick(email: String, password: String)
    }
}