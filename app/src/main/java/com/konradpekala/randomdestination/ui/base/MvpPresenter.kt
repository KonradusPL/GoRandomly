package com.konradpekala.randomdestination.ui.base

interface MvpPresenter<V: MvpView> {
    fun onCreate()
    fun start()
    fun stop()
}