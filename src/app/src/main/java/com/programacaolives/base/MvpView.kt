package com.programacaolives.base

interface MvpView<T : MvpPresenter> {
    fun initializePresenter(presenter: T)
}