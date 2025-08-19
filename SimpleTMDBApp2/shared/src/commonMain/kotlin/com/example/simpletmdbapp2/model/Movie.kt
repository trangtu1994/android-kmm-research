package com.example.simpletmdbapp2.model

data class Movie (
    val id: Long,
    val name: String = "",
    val description: String = "",
    val backdrop: String?,
    val poster: String?,
    val year: String,
    val vote: Float,
)

