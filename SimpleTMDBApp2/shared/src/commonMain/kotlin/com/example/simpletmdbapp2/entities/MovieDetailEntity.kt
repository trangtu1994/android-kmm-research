package com.example.simpletmdbapp2.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailEntity(
    val id: Long,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val budget: Long?,
    @SerialName("belongs_to_collection")
    var belongCollection: CollectionEntity?,
    val genres: List<GenreEntity>? = emptyList(),
    val homepage: String?,
    @SerialName("origin_country")
    val originCountry: List<String>?,
    @SerialName("original_language")
    val originLanguage: String?,
    @SerialName("original_title")
    val originTitle: String?,
    val overview: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    val popularity: Float?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("revenue")
    val revenue: Long?,
    val runtime: Int?,
    val status: String?,
    val title: String?,
    val tagline: String?,
    @SerialName("vote_average")
    val voteAverage: Float?,
    @SerialName("vote_count")
    val voteCount: Float?,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageEntity>?,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyEntity>?,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryEntity>?)

@Serializable
data class CollectionEntity(
    val id: Long,
    val name: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
)

@Serializable
data class GenreEntity(
    val id: Long, val name: String
)

@Serializable
data class SpokenLanguageEntity(
    @SerialName("english_name")
    val englishName: String?,
    @SerialName("iso_639_1")
    val iso6391: String,
    val name: String?
)

@Serializable
data class ProductionCompanyEntity(
    val id: Long,
    val name: String?,
    @SerialName("origin_country")
    val originalCountry: String?,
    @SerialName("logo_path")
    val logoPath: String?,
)

@Serializable
data class ProductionCountryEntity(
    val name: String?,
    @SerialName("iso_3166_1")
    val iso31661: String?,
)
