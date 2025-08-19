package com.example.simpletmdbapp2.network

import com.example.simpletmdbapp2.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TMDBClient(
    val configuration: NetworkConfiguration
) {

    val httpClient = HttpClient {
        HttpResponseValidator {
            validateResponse { response ->
                if (response.status.value >= 400) {
                    // Custom handling for 403 Forbidden
                    println("Access forbidden!")
                    throw Exception("Forbidden access") // Or a custom exception
                }
            }
        }
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = true
            })
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org/3"
            }
            header("accept", "application/json")
            header("Authorization", "${configuration.apiKey}")
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }

    }

    suspend fun getAllMovies(): List<Movie> {
        return httpClient.get("trending/movie/day?language=en-US").body()
    }
}