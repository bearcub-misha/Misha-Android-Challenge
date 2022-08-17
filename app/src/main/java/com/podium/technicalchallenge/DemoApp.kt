package com.podium.technicalchallenge

import android.app.Application
import com.podium.technicalchallenge.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DemoApp)
            modules(appModule)
        }
    }
}