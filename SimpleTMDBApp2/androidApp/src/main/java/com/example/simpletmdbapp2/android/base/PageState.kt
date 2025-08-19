package com.example.simpletmdbapp2.android.base


interface ErrorState

data class ErrorDataState(
    val message: String,
    val description: String? = null
): ErrorState

data class PageState(
    val loading: Boolean,
    val error: ErrorState? = null
)