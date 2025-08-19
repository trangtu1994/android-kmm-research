package com.example.simpletmdbapp2.data.service

import com.example.simpletmdbapp2.entities.MovieDetailEntity
import com.example.simpletmdbapp2.entities.MovieEntity
import com.example.simpletmdbapp2.entities.PagingResponse
import com.example.simpletmdbapp2.network.TMDBClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface MovieService {
    suspend fun getFavoriteMovies(page: Int) : PagingResponse<MovieEntity>
    suspend fun searchMovies(searchTerm: String) : PagingResponse<MovieEntity>
    suspend fun getMovieDetail(movieId: Long) : MovieDetailEntity?
}

class MovieServiceImpl(
    val tmdbClient: TMDBClient,
): MovieService {

    val client : HttpClient = tmdbClient.httpClient

    suspend fun getAllMovies(): PagingResponse<MovieEntity> {
        return client.get("trending/movie/day?language=en-US").body()
    }

    override suspend fun getFavoriteMovies(page: Int): PagingResponse<MovieEntity> {
        return getAllMovies()
    }

    override suspend fun searchMovies(searchTerm: String): PagingResponse<MovieEntity> {
        return client.get ("search/movie?query=$searchTerm&include_adult=false&language=en-US&page=1" ).body()
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetailEntity? {
        return client.get("movie/$movieId?language=en-US").body()
    }
}