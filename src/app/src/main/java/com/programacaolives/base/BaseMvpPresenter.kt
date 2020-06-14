package com.programacaolives.base

abstract class BaseMvpPresenter<T>(
    protected var view: T?
) : MvpPresenter {
    override fun onDestroy() {
        view = null
    }
}