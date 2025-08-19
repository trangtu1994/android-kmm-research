package com.example.simpletmdbapp2.kotinext

fun Double?.safe(default: Double = 0.0): Double {
    return this ?: default
}

