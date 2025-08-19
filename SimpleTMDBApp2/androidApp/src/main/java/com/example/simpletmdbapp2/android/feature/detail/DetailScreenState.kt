package com.example.simpletmdbapp2.android.feature.detail

data class DetailScreenState (
    val movieDetail: DetailState = MovieDetailState()
)

interface DetailState

data class MovieDetailState(
    val id: Long = 0,
    val name: String = "",
    val backDropPath: String = "",
    val posterPath: String = "",
    val releaseDate: String = "",
    val description: String = "",
    val otherData: List<MovieDataState> = emptyList()
): DetailState

data class ErrorDetailState(
    val message: String?,
    val description: String? = null,
): DetailState

data class MovieDataState(
    val key: String,
    val stringValue: String?
)