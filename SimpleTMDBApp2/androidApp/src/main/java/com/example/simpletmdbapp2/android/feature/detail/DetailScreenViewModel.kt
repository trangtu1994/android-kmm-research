package com.example.simpletmdbapp2.android.feature.detail

import androidx.lifecycle.SavedStateHandle
import com.example.simpletmdbapp2.android.base.BaseViewModel
import com.example.simpletmdbapp2.android.base.ErrorState
import com.example.simpletmdbapp2.android.extension.formatAmount
import com.example.simpletmdbapp2.android.extension.toDate
import com.example.simpletmdbapp2.android.extension.toString
import com.example.simpletmdbapp2.android.helper.AppNavArgName.ITEM_ID_ARG
import com.example.simpletmdbapp2.android.helper.CONFIG_DATE_FORMAT
import com.example.simpletmdbapp2.android.helper.CONFIG_TIME_CACHE_MIL_SEC
import com.example.simpletmdbapp2.android.helper.LAST_CACHE_ID_TIME
import com.example.simpletmdbapp2.data.SharedPreferenceRepository
import com.example.simpletmdbapp2.kotinext.safe
import com.example.simpletmdbapp2.sdk.TMDBSimpleSDK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class DetailScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val preferenceRepository: SharedPreferenceRepository,
    private val sdk: TMDBSimpleSDK,
) : BaseViewModel() {

    private val _detailState = MutableStateFlow(DetailScreenState())
    val state = _detailState.asStateFlow()

    private val movieId = savedStateHandle.get<Long>(ITEM_ID_ARG)

    init {
        loadMovieById(movieId ?: 0)
    }

    private fun loadMovieById(id: Long, forceLoad: Boolean = false) {
        launch (onError = { error ->
            _detailState.value = _detailState.value.copy(
                movieDetail = onNotFoundMovie()
            )
        }) {
            val movie = sdk.getMovieById(id, forceLoad || checkShouldReload())
            updateMovieState(movie)
        }
    }

    fun refreshMovieDetail() {
        loadMovieById(movieId ?: 0, true)
    }

    private fun updateMovieState(movie: com.example.simpletmdbapp2.model.MovieDetail?) {
        _detailState.value = _detailState.value.copy(
            movieDetail = if (movie == null) onNotFoundMovie()  else MovieDetailState(
                id = movie.id,
                name = movie.title.safe(),
                backDropPath = movie.backdropPath.safe(),
                posterPath = movie.posterPath.safe(),
                description = movie.overview.safe(),
                releaseDate = movie.releaseDate?.toDate("yyyy-MM-yy")?.toString(CONFIG_DATE_FORMAT).safe(),
                otherData = listOf(
                    MovieDataState("Vote Count", "${movie.voteCount?.toInt()}"),
                    MovieDataState("Vote Average", "${movie.voteAverage}"),
                    MovieDataState("Country", movie.originCountry.orEmpty().joinToString(", ")),
                    MovieDataState("Language", movie.originLanguage),
                    MovieDataState("Budget", "${movie.budget?.formatAmount()}"),
                    MovieDataState("Revenue", "${movie.revenue?.formatAmount()}"),
                    MovieDataState("Status", movie.status.safe()),
                    MovieDataState("Popularity", movie.popularity.toString()),)
            )
        )
    }

    private fun checkShouldReload() : Boolean {
        val cacheKey = "${LAST_CACHE_ID_TIME}_$movieId"
        val lastLong = preferenceRepository.getLong(cacheKey)
        val date = if (lastLong > 0) Date() else null
        val isExpired = date == null || (date.time - lastLong) > CONFIG_TIME_CACHE_MIL_SEC
        if (isExpired) {
            preferenceRepository.saveLong(cacheKey, Date().time)
        }
        return isExpired
    }


    private fun onNotFoundMovie(): DetailState {
        return ErrorDetailState("Can not found movie detail",
            description = "there are no id ($movieId) movie")
    }

}