package com.example.simpletmdbapp2.android

import android.app.Application
import com.example.simpletmdbapp2.android.di.appModule
import com.example.simpletmdbapp2.android.di.viewModelModule
import com.example.simpletmdbapp2.di.SharedModuleFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
            modules(viewModelModule)
            modules(SharedModuleFactory().getSharedAppModules())
        }
    }
}