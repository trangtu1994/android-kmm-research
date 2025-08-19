package com.example.simpletmdbapp2.domain

import com.example.simpletmdbapp2.entities.ConfigurationEntity
import com.example.simpletmdbapp2.model.Configuration

interface CommonConfigurationRepository {

    suspend fun loadConfiguration() : ConfigurationEntity

    suspend fun loadAndSaveConfiguration()

    fun getCachedConfiguration(): Configuration
}