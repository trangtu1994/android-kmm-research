package com.example.simpletmdbapp2.kotinext


fun Int?.safe(default: Int = 0): Int {
    return this ?: default
}

