package com.programacaolives.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : MvpPresenter> : AppCompatActivity(), MvpView<T> {
    protected lateinit var presenter: T
    protected var isCreatingActivity = true
    protected var isActivitySetup = false

    override fun initializePresenter(presenter: T) {
        this.presenter = presenter
    }

    override fun onPause() {
        super.onPause()

        isCreatingActivity = false
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}