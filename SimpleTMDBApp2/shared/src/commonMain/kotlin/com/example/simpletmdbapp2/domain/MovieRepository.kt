package com.example.simpletmdbapp2.domain

import com.example.simpletmdbapp2.entities.MovieEntity
import com.example.simpletmdbapp2.entities.PagingResponse
import com.example.simpletmdbapp2.model.Movie
import com.example.simpletmdbapp2.model.MovieDetail
import com.example.simpletmdbapp2.model.PagingModel

interface MovieRepository  {

    suspend fun getFavoriteMovies(searchTerm: String, isNoFromCache: Boolean): PagingModel<Movie>
    suspend fun getMovie(id: Long, isNoFromCache: Boolean): MovieDetail?
    suspend fun searchMovies(searchTerm: String): PagingResponse<MovieEntity>
    suspend fun cacheMovies(movieEntities: List<MovieEntity>)
}