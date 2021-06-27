package com.example.mysubscribers.di

import android.app.Application
import com.example.mysubscribers.di.modules.dataBaseModule
import com.example.mysubscribers.di.modules.repositoryModule
import com.example.mysubscribers.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initiateKoin()
    }

    private fun initiateKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                viewModelModule,
                dataBaseModule,
                repositoryModule
            )
        }
    }
}