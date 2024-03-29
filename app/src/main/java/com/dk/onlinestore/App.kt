package com.dk.onlinestore

import android.app.Application
import com.dk.onlinestore.di.appModule
import com.dk.onlinestore.di.catalogModule
import com.dk.onlinestore.di.favoriteModule
import com.dk.onlinestore.di.loginModule
import com.dk.onlinestore.di.productModule
import com.dk.onlinestore.di.profileModule
import com.dk.onlinestore.di.retrofitModule
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
            modules(
                appModule, loginModule, catalogModule, retrofitModule, productModule,
                profileModule, favoriteModule
            )
        }
    }

}