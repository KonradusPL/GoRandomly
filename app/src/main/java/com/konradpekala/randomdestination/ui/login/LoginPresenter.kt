package com.konradpekala.randomdestination.ui.login

import android.util.Log
import com.konradpekala.randomdestination.R
import com.konradpekala.randomdestination.data.repos.LoginRepository
import com.konradpekala.randomdestination.ui.base.BasePresenter

class LoginPresenter<V: LoginMvp.View>(view: V,val repo: LoginRepository): BasePresenter<V>(view),LoginMvp.Presenter<V> {

    override fun onSignUpButtonClick(email: String, password: String) {
        if (password.length < 6){
            view.showMessage(R.string.password_6_chars)
        }

        view.showLoading()
        cd.add(repo.signUp(email, password)
            .flatMap { t: String -> repo.createUser(email,password,t) }
            .subscribe({
                view.hideLoading()
                view.showMessage(R.string.success)
                view.openMainActivity()
            },{t: Throwable? ->
                Log.d("onSignUpButtonClick",t.toString())
                view.hideLoading()
                view.showMessage(R.string.create_account_error)
                view.showMessage(t.toString())
            }))
    }

    override fun onSignInButtonClick(email: String, password: String) {
        view.showLoading()
        cd.add(repo.signIn(email, password)
            .doOnComplete { view.hideLoading() }
            .subscribe({
                view.hideLoading()
                view.showMessage(R.string.success)
                view.openMainActivity()
            },{t: Throwable? ->
                view.hideLoading()
                view.showMessage(R.string.sign_in_error)
            }))
    }

}