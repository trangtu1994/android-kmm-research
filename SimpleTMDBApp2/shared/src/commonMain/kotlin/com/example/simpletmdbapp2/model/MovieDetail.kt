package com.example.simpletmdbapp2.model

data class MovieDetail(
    val id: Long,
    val title: String?,
    val originTitle: String?,
    val overview: String?,
    val budget: Long?,
    val backdropPath: String?,
    val posterPath: String?,
    val status: String?,
    val revenue: Long?,
    val releaseDate: String?,
    val voteAverage: Float?,
    val voteCount: Float?,
    val originCountry: List<String>?,
    val originLanguage: String?,
    val popularity: Float?,
    val runtime: Int?,
    val tagline: String?)