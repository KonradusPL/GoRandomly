package com.konradpekala.randomdestination.ui.login

import com.konradpekala.randomdestination.data.repos.LoginRepository
import com.konradpekala.randomdestination.ui.base.BasePresenter

class LoginPresenter<V: LoginMvp.View>(view: V,val repo: LoginRepository): BasePresenter<V>(view),LoginMvp.Presenter<V> {

    override fun onSignUpButtonClick(email: String, password: String) {
        cd.add(repo.signUp(email, password)
            .subscribe({
                view.showMessage("Udało się stworzyć konto")
                view.openMainActivity()
            },{t: Throwable? ->
                view.showMessage("Błąd! Upewnij się że twoje hasło posiada cyfry")
            }))
    }

    override fun onSignInButtonClick(email: String, password: String) {
        cd.add(repo.signIn(email, password)
            .subscribe({
                view.showMessage("Udało się zalogować")
                view.openMainActivity()
            },{t: Throwable? ->
                view.showMessage("error")
            }))
    }

}