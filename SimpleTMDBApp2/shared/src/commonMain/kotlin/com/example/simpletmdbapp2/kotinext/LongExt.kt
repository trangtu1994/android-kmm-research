package com.example.simpletmdbapp2.kotinext

fun Long?.safe(default: Long = 0): Long {
    return this ?: default
}


