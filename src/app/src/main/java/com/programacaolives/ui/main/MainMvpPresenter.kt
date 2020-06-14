package com.programacaolives.ui.main

import com.programacaolives.base.MvpPresenter

interface MainMvpPresenter : MvpPresenter {
    fun onCreate(isOnline: Boolean)
    fun onStop()
    fun onOptionsItemSelected(itemId: Int): Boolean
}