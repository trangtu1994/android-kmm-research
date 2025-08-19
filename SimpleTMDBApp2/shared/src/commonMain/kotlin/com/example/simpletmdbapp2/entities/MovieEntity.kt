package com.example.simpletmdbapp2.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieEntity(
    val adult: Boolean,
    val video: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    val id: Long,
    val title: String? = null,
    val overview: String? = null,
    @SerialName("original_title")
    val orgTitle: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("original_language")
    val orgLanguage: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Long>,
    val popularity: Float,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("vote_average")
    val voteAverage: Double,
)


