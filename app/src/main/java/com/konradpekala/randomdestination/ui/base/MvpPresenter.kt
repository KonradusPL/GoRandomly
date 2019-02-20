package com.konradpekala.randomdestination.ui.base

interface MvpPresenter<V: MvpView> {
    fun onCreate()
    fun onDestroy()
    fun start()
    fun stop()
}