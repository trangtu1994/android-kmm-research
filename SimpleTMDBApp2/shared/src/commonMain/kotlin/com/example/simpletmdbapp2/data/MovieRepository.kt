package com.example.simpletmdbapp2.data

import com.example.simpletmdbapp2.data.database.MovieLocalService
import com.example.simpletmdbapp2.data.service.MovieService
import com.example.simpletmdbapp2.domain.CommonConfigurationRepository
import com.example.simpletmdbapp2.domain.MovieRepository
import com.example.simpletmdbapp2.entities.CollectionEntity
import com.example.simpletmdbapp2.entities.MovieDetailEntity
import com.example.simpletmdbapp2.entities.MovieEntity
import com.example.simpletmdbapp2.entities.PagingResponse
import com.example.simpletmdbapp2.kotinext.safe
import com.example.simpletmdbapp2.model.Movie
import com.example.simpletmdbapp2.model.MovieDetail
import com.example.simpletmdbapp2.model.PagingModel
import com.example.simpletmdbapp2.model.PickingSize

class MovieRepositoryImp (
    val service: MovieService,
    val localService: MovieLocalService,
    val configurationRepository: CommonConfigurationRepository,
): MovieRepository {

    override suspend fun getFavoriteMovies(
        searchTerm: String,
        isNoFromCache: Boolean
    ): PagingModel<Movie> {
        val entities = if (searchTerm.isNotBlank()) searchMovies(searchTerm) else getFavoriteMovies(isNoFromCache)
        return PagingModel(
            entities.page.safe(),
            entities.total_results.safe(),
            entities.results.orEmpty().map {
                Movie(it.id,
                    name = it.title ?: it.orgTitle.safe(),
                    description = it.overview.safe(),
                    backdrop = createImageUrl(it.backdropPath,  pickingSize = PickingSize.S_700),
                    poster = createImageUrl(it.posterPath, pickingSize = PickingSize.S_700),
                    year = it.releaseDate.safe(),
                    vote = it.voteAverage.toFloat()
                )
            },
        )
    }

    override suspend fun getMovie(id: Long, isNoFromCache: Boolean): MovieDetail? {
        val latestCached = localService.takeIf { !isNoFromCache }?.getMovieDetailById(id) ?: loadCachedMovieDetailEntity(id)
        return latestCached?.let {
            MovieDetail(
                id = id,
                title = it.title,
                originTitle = it.originTitle,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                budget = it.budget,
                status = it.status,
                revenue = it.revenue,
                voteCount = it.voteCount,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                originCountry = it.originCountry,
                originLanguage = it.originLanguage,
                popularity = it.popularity,
                runtime = it.runtime,
                tagline = it.tagline
            )
        }
    }

    private suspend fun loadCachedMovieDetailEntity(id: Long): MovieDetailEntity? {
        return service.getMovieDetail(id)?.let {
            it.belongCollection = it.belongCollection ?: CollectionEntity(-1, "Not", "/", "/")
            localService.cacheMovieDetail(it)
            localService.getMovieDetailById(id)
        }
    }

    private suspend fun getFavoriteMovies(forceLoad: Boolean) : PagingResponse<MovieEntity> {
        val cacheMovies = if (forceLoad) emptyList() else localService.getCachedMovies()
        return if (!cacheMovies.isNullOrEmpty()) {
            PagingResponse(0, cacheMovies.size, -1, cacheMovies)
        } else {
            fetchAndCacheMovies()
            getFavoriteMovies(false)
        }
    }

    private suspend fun fetchAndCacheMovies(): PagingResponse<MovieEntity> {
        val pagingModel = service.getFavoriteMovies(0)
        if (pagingModel.results.orEmpty().isNotEmpty()) {
            cacheMovies(pagingModel.results!!)
        }
        return pagingModel
    }


    override suspend fun searchMovies(searchTerm: String): PagingResponse<MovieEntity> {
        return service.searchMovies(searchTerm)
    }

    override suspend fun cacheMovies(movieEntities: List<MovieEntity>) {
        localService.cacheMovieEntities(movieEntities)
    }

    private fun createImageUrl(url: String?,
                               isDefaultSize: Boolean = false,
                               pickingSize: PickingSize = PickingSize.S_SMALLEST): String? {
        url ?: return null
        val config = configurationRepository.getCachedConfiguration()
        val baseUrl = config.imageBase
        val size =(if (isDefaultSize) config.profileSizes else config.movieSizes)
            .firstOrNull { it.pickingSize == pickingSize }?.value ?: PickingSize.ORIGINAL.value
        return "$baseUrl${size}$url"
    }
}