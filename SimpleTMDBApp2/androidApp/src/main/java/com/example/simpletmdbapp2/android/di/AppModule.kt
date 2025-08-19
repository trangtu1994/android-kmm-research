package com.example.simpletmdbapp2.android.di

import com.example.simpletmdbapp2.AndroidDatabaseDriverFactory
import com.example.simpletmdbapp2.android.appdata.PreferenceManager
import com.example.simpletmdbapp2.cache.DatabaseDriverFactory
import com.example.simpletmdbapp2.data.service.SharePreferenceManager
import com.example.simpletmdbapp2.sdk.TMDBSimpleSDK
import com.example.simpletmdbapp2.network.NetworkConfiguration
import com.example.simpletmdbapp2.network.TMDBClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<NetworkConfiguration> {
        NetworkConfiguration(
            baseUrl = ",=",
            apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MGZhYzE0YjBjM2VlYTFmYzIxMTJmZTE2MmJmZTU2YyIsIm5iZiI6MTcyMDM0NTcyMy40MTY5OTk4LCJzdWIiOiI2NjhhNjQ3YjM2MTQ1NDc3Mjk2MTdiZWQiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.79k-btJ2WgwVFTCCA96Gnbyk3SdZn62DKlBJIFvQ2LU")
    }
    single<TMDBClient> { TMDBClient(get()) }

    single<TMDBSimpleSDK> {
        TMDBSimpleSDK(get(), get())
    }

    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(androidContext())
    }

    single<SharePreferenceManager>() { PreferenceManager(androidContext()) }
}

