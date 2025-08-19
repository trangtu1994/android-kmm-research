package com.example.simpletmdbapp2.cache

import com.example.simpletmdbapp2.entities.MovieDetailEntity
import com.example.simpletmdbapp2.entities.MovieEntity
import com.example.simpletmdbapp2.kotinext.safe
import com.jetbrains.tbdmmovies.cache.CollectionEntity
import com.jetbrains.tbdmmovies.cache.SelectMoviesDetailById
import com.jetbrains.tmbdmovies.cache.AppDatabase


internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver()) //AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllMovies(): List<MovieEntity> {
        return dbQuery.selectAllMovies(::mapToMovie).executeAsList()
    }

    private fun mapToMovie(
        id: Long,
        backdropPath: String?,
        posterPath: String?,
        popularity: Double?,
        title: String,
        orgTitle: String,
        overview: String?,
        releaseDate: String?,
        voteAverage: Double?
    ): MovieEntity {
        return MovieEntity(
            id = id,
            title = title,
            backdropPath = backdropPath,
            posterPath = posterPath,
            popularity = popularity.safe().toFloat(),
            orgTitle = orgTitle,
            overview = overview.safe(), adult = false, video = false,
            genreIds = emptyList(),
            releaseDate = releaseDate.safe(),
            voteAverage = voteAverage.safe()
        )
    }

    internal fun clearAndCreateMovie(movies: List<MovieEntity>) {
        dbQuery.transaction {
            dbQuery.removeAllMovies()
            movies.forEach { movie ->
                dbQuery.insertMovie(
                    movie.id, movie.backdropPath, movie.posterPath,
                    movie.popularity.safe().toDouble(), movie.title.safe(), movie.orgTitle.safe(),
                    movie.overview,
                    movie.releaseDate,
                    movie.voteAverage
                )
            }
        }
    }

    internal fun insertMovieDetail(movieDetail: MovieDetailEntity) {
        dbQuery.insertCollection(
            movieDetail.belongCollection?.id ?: 0,
            movieDetail.belongCollection?.name ?: "",
            movieDetail.belongCollection?.posterPath ?: "",
            movieDetail.belongCollection?.backdropPath ?: "",
        )

        dbQuery.insertMovieDetai(
            movieDetail.id,
            movieDetail.backdropPath,
            movieDetail.budget,
            movieDetail.belongCollection?.id ?: 0,
            movieDetail.homepage,
            movieDetail.originCountry?.joinToString(","),
            movieDetail.originLanguage,
            movieDetail.originTitle,
            movieDetail.overview,
            movieDetail.posterPath,
            movieDetail.popularity?.toDouble(),
            movieDetail.releaseDate,
            movieDetail.revenue,
            movieDetail.runtime?.toLong(),
            movieDetail.status,
            movieDetail.title,
            movieDetail.tagline,
            movieDetail.voteAverage?.toDouble(),
            movieDetail.voteCount?.toDouble(),
        )
    }

    internal fun getMovieById(id: Long): MovieDetailEntity? {
        val movie = dbQuery.selectMoviesDetailById(id).executeAsOneOrNull()
        movie ?: return null
        val collection = dbQuery.selectCollectionById(movie.collectionId_).executeAsOneOrNull()
        return mapToMovieDetail(id, movie, mapToCollection(collection))
    }


    private fun mapToCollection(collectionEntity: CollectionEntity?): com.example.simpletmdbapp2.entities.CollectionEntity? {
        return collectionEntity?.let {
            com.example.simpletmdbapp2.entities.CollectionEntity(
                it.id, it.name, it.backdropPath, it.backdropPath
            )
        }
    }

    private fun mapToMovieDetail(id: Long,
                                 dbEntity: SelectMoviesDetailById,
                                 collectionEntity: com.example.simpletmdbapp2.entities.CollectionEntity?
    ): MovieDetailEntity {
        return MovieDetailEntity(
            id= id,
            title = dbEntity.title,
            backdropPath = dbEntity.backdropPath,
            posterPath = dbEntity.posterPath,
            popularity = dbEntity.popularity.safe().toFloat(),
            originTitle = dbEntity.originTitle,
            overview = dbEntity.overview.safe(),
            belongCollection = collectionEntity,
            budget = dbEntity.budget,
            homepage = dbEntity.homepage,
            originCountry = dbEntity.originCountry?.split(","),
            originLanguage = dbEntity.originLanguage,
            releaseDate = dbEntity.releaseDate,
            revenue = dbEntity.revenue,
            runtime = dbEntity.runtime?.toInt(),
            status = dbEntity.status,
            tagline = dbEntity.tagline,
            voteAverage = dbEntity.voteAverage.safe().toFloat(),
            voteCount = dbEntity.voteCount.safe().toFloat(),
            genres = emptyList(),
            spokenLanguages = emptyList(),
            productionCompanies = emptyList(),
            productionCountries = emptyList()
        )
    }
}