package com.konradpekala.randomdestination.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V: MvpView>(var view: V): MvpPresenter<V> {

    protected val cd = CompositeDisposable()

    override fun onCreate() {

    }

    override fun onDestroy() {
        cd.clear()
    }

    override fun start() {
    }

    override fun stop() {
    }


}