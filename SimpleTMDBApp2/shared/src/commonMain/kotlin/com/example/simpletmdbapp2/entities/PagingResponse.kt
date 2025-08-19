package com.example.simpletmdbapp2.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse<T> (
    @SerialName("page")
    var page: Int?,
    @SerialName("total_pages")
    var total_pages: Int?,
    @SerialName("total_results")
    var total_results: Int?,
    val results: List<T>? = null
)