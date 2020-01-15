package com.wreckingball.magicdex

import android.app.Application
import com.wreckingball.magicdex.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MagicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MagicApp)
            modules(appModule)
        }
    }
}