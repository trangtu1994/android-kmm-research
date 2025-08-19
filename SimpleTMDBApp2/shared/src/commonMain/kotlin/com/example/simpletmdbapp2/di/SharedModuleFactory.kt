package com.example.simpletmdbapp2.di

import org.koin.core.module.Module

class SharedModuleFactory {

    fun getSharedAppModules() : List<Module> {
        return listOf(
            dataServiceModule,
            repositoryModule
        )
    }
}