package com.programacaolives.ui.main

import com.programacaolives.base.MvpView
import com.programacaolives.domain.entity.Live

interface MainMvpView : MvpView<MainMvpPresenter> {
    fun setupMainNormalState(lives: List<Live>)
    fun setupMainEmptyState()
    fun setupMainErrorState()
    fun initializeActivityWithInternet()
    fun initializeActivityWithoutInternet()
    fun navigateToAppReview(url: String)
    fun navigateToSharingScreen(url: String)
}