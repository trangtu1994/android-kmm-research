package com.example.simpletmdbapp2.di

import com.example.simpletmdbapp2.data.CommonConfigurationRepositoryImpl
import com.example.simpletmdbapp2.data.MovieRepositoryImp
import com.example.simpletmdbapp2.data.SharedPreferenceRepository
import com.example.simpletmdbapp2.data.database.MovieLocalService
import com.example.simpletmdbapp2.data.service.ConfigurationService
import com.example.simpletmdbapp2.data.service.ConfigurationServiceImpl
import com.example.simpletmdbapp2.data.service.MovieService
import com.example.simpletmdbapp2.data.service.MovieServiceImpl
import com.example.simpletmdbapp2.domain.CommonConfigurationRepository
import com.example.simpletmdbapp2.domain.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MovieRepository> {
        MovieRepositoryImp(service = get(),
            localService = get(),
            configurationRepository = get())
    }

    single<CommonConfigurationRepository> {
        CommonConfigurationRepositoryImpl(service = get())
    }

    single<SharedPreferenceRepository> {
        SharedPreferenceRepository(get())
    }
}

val dataServiceModule = module {

    single<MovieService> {
        MovieServiceImpl(tmdbClient = get())
    }

    single<ConfigurationService> {
        ConfigurationServiceImpl(tmdbClient = get() )
    }

    single<MovieLocalService> {
        MovieLocalService(get())
    }
}

