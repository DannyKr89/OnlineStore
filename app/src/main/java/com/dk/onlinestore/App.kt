package com.dk.onlinestore

import android.app.Application
import com.dk.onlinestore.di.appModule
import com.dk.onlinestore.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(appModule, loginModule)
        }
    }

}