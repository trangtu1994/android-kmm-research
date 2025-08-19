package com.example.simpletmdbapp2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform