package com.example.simpletmdbapp2.model

data class PagingModel<T>(
    val page: Int,
    val totalResult: Int,
    val result: List<T>
)