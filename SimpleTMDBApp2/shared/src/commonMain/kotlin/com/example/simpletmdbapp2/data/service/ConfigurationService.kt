package com.example.simpletmdbapp2.data.service

import com.example.simpletmdbapp2.entities.ConfigurationEntity
import com.example.simpletmdbapp2.network.TMDBClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface ConfigurationService {
    suspend fun getConfiguration() : ConfigurationEntity
}

class ConfigurationServiceImpl (
    val tmdbClient: TMDBClient
): ConfigurationService {

    override suspend fun getConfiguration(): ConfigurationEntity {
        return  tmdbClient.httpClient.get("configuration").body()
    }
}