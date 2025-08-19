package com.example.simpletmdbapp2.model

data class Configuration (
    val imageBase: String,
    val posterSize: String,
    val profileSizes: List<ImageSize>,
    val movieSizes: List<ImageSize>
)

class ImageSize(
    val value: String,
    val pickingSize: PickingSize
) {
    val isOriginalSize: Boolean
        get() = value == "original"
}

enum class PickingSize(val value: String) {
    S_500 ("5"),
    S_SMALLEST ("0"),
    S_700 ("700"),
    ORIGINAL("original")
}
