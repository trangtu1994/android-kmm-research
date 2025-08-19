package com.example.simpletmdbapp2.android.feature.movie

import com.example.simpletmdbapp2.model.Movie
import com.example.simpletmdbapp2.entities.MovieEntity
import com.example.simpletmdbapp2.network.NetworkConfiguration

interface MovieMapper {
    fun fromEntitiesToMovies(entities: List<MovieEntity>): List<Movie>
}

class MovieMapperImp(
    val networkConfiguration: NetworkConfiguration
): MovieMapper {

    override fun fromEntitiesToMovies(entities: List<MovieEntity>): List<Movie> {
        return entities.map { Movie(
            id = it.id,
            name = it.title ?: "No title",
            backdrop = "$networkConfiguration",
            poster = "", year = "", vote = 0f
        ) }
    }

}