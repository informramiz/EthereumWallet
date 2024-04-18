package com.ramiz.ethereumwallet

import android.app.Application
import timber.log.Timber

class EthereumWalletApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
