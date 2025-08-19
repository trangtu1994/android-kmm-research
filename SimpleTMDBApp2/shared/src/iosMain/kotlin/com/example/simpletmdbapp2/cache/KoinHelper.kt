package com.example.simpletmdbapp2.cache

import com.example.simpletmdbapp2.model.Movie
import com.example.simpletmdbapp2.model.MovieDetail
import com.example.simpletmdbapp2.sdk.TMDBSimpleSDK
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinHelper: KoinComponent {
    private val sdk: TMDBSimpleSDK by inject<TMDBSimpleSDK>()

    suspend fun getMovies(search: String, forceReload: Boolean): List<Movie> {
        return sdk.getFavoriteMovies(search, forceReload).result
    }

    suspend fun getMovieById(id: Long, forceReload: Boolean): MovieDetail? {
        return sdk.getMovieById(id, forceReload)
    }
}