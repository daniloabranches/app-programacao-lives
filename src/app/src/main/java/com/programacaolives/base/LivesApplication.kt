package com.programacaolives.base

import androidx.multidex.MultiDexApplication
import com.programacaolives.log.Log
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class LivesApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler { e: Throwable ->
            Log.exception(e)
        }
    }
}