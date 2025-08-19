package com.example.simpletmdbapp2.cache

import com.example.simpletmdbapp2.sdk.TMDBSimpleSDK
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(module {
            single<DatabaseDriverFactory> { IOSDatabaseDriverFactory()  }
            single<TMDBSimpleSDK> {
                TMDBSimpleSDK( get(), get())
            }
        })
    }
}