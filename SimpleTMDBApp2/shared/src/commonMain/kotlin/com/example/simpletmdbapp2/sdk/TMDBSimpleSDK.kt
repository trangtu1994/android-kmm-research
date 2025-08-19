package com.example.simpletmdbapp2.sdk

import com.example.simpletmdbapp2.domain.CommonConfigurationRepository
import com.example.simpletmdbapp2.domain.MovieRepository
import com.example.simpletmdbapp2.model.Movie
import com.example.simpletmdbapp2.model.MovieDetail
import com.example.simpletmdbapp2.model.PagingModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TMDBSimpleSDK (
    private val movieRepository: MovieRepository,
    private val configurationRepository: CommonConfigurationRepository
) {

    @Throws(Exception::class)
    fun initSdkConfiguration(scope: CoroutineScope,
                             completion: OnCompletionListener) {
        scope.launch {
            try {
                configurationRepository.loadAndSaveConfiguration()
                completion.invoke(SuccessResult(true))
            } catch (e: Exception) {
                completion.invoke(ErrorResult(e))
            }
        }
    }

    suspend fun getFavoriteMovies(searchTerm: String, isForceLoad: Boolean): PagingModel<Movie> {
        return movieRepository.getFavoriteMovies(searchTerm, isForceLoad)
    }

    suspend fun getMovieById(id: Long, isForceLoad: Boolean): MovieDetail? {
        return movieRepository.getMovie(id, isForceLoad)
    }

}