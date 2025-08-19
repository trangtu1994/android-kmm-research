package com.example.simpletmdbapp2.data.database

import com.example.simpletmdbapp2.cache.Database
import com.example.simpletmdbapp2.cache.DatabaseDriverFactory
import com.example.simpletmdbapp2.entities.MovieDetailEntity
import com.example.simpletmdbapp2.entities.MovieEntity

class MovieLocalService(databaseDriverFactory: DatabaseDriverFactory) {

    private val database: Database = Database(databaseDriverFactory)

    fun cacheMovieEntities(entities: List<MovieEntity>) {
        database.clearAndCreateMovie(entities)
    }

    fun getCachedMovies(): List<MovieEntity> {
        return database.getAllMovies()
    }

    fun getMovieDetailById(id: Long): MovieDetailEntity? {
        return database.getMovieById(id)
    }

    fun cacheMovieDetail(movieDetail: MovieDetailEntity) {
        database.insertMovieDetail(movieDetail)
    }
}