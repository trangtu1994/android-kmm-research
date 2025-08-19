package com.example.simpletmdbapp2.android.di

import com.example.simpletmdbapp2.android.feature.activity.MainViewModel
import com.example.simpletmdbapp2.android.feature.detail.DetailScreenViewModel
import com.example.simpletmdbapp2.android.feature.movie.MovieMapper
import com.example.simpletmdbapp2.android.feature.movie.MovieMapperImp
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    single<MovieMapper> {
        MovieMapperImp(get())
    }

    viewModel { MainViewModel(
        sdk = get(),
        preferenceRepository = get() )
    }


    viewModelOf(::DetailScreenViewModel)
}