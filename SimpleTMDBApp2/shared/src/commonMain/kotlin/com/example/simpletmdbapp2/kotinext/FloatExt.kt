package com.example.simpletmdbapp2.kotinext

fun Float?.safe(default: Float = 0f): Float {
    return this ?: default
}

