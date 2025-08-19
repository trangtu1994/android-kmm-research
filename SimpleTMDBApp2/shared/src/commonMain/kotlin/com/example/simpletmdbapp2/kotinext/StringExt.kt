package com.example.simpletmdbapp2.kotinext

fun String?.safe(default: String = ""): String {
    return this ?: default
}

