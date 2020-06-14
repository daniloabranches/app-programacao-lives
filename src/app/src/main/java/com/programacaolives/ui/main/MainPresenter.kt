package com.programacaolives.ui.main

import com.programacaolives.R
import com.programacaolives.base.BaseMvpPresenter
import com.programacaolives.configuration.DependencyInjector
import com.programacaolives.domain.entity.Live
import com.programacaolives.log.Log
import com.programacaolives.utils.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainPresenter(
    view: MainMvpView?,
    dependencyInjector: DependencyInjector,
    private val schedulerProvider: SchedulerProvider
) : BaseMvpPresenter<MainMvpView>(view),
    MainMvpPresenter {

    private val compositeDisposable = CompositeDisposable()

    private val appUrl = "https://play.google.com/store/apps/details?id=com.programacaolives"

    private val getScheduleUseCase by lazy {
        dependencyInjector.getScheduleUseCase()
    }

    override fun onCreate(isOnline: Boolean) {
        if (isOnline) {
            view?.initializeActivityWithInternet()

            val subscriptionGetScheduleUseCase = getScheduleUseCase()
                .retry(1)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        if (it.isNotEmpty()) {
                            view?.setupMainNormalState(sortLivesByDate(it))
                        } else {
                            view?.setupMainEmptyState()
                        }
                    },
                    {
                        Log.exception(it)
                        view?.setupMainErrorState()
                    })
            compositeDisposable.add(subscriptionGetScheduleUseCase)
        } else {
            view?.initializeActivityWithoutInternet()
        }
    }

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun onOptionsItemSelected(itemId: Int): Boolean {
        return when (itemId) {
            R.id.rate_menu -> {
                rateApp()
                true
            }
            R.id.share_menu -> {
                shareApp()
                true
            }
            else -> false
        }
    }

    private fun rateApp() = view?.navigateToAppReview(appUrl)

    private fun shareApp() = view?.navigateToSharingScreen(appUrl)

    private fun sortLivesByDate(lives: List<Live>) = lives.sortedBy { it.date }
}