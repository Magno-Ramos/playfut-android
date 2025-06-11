package com.magnus.playfut

import android.app.Application
import com.google.firebase.FirebaseApp
import com.magnus.playfut.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class PlayFutApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        GlobalContext.startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@PlayFutApplication)
            // Load modules
            modules(appModules)
        }
    }
}