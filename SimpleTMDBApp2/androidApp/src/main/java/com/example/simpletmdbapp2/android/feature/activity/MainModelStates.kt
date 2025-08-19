package com.example.simpletmdbapp2.android.feature.activity

import com.example.simpletmdbapp2.model.Movie

data class MainModelState (
    val isSuccessful: Boolean = false,
    val pageSize: Int = 0,
    val launches: List<Movie> = emptyList(),
    val searching: String? = ""
)
