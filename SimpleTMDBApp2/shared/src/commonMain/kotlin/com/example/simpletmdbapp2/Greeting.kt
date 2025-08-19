package com.example.simpletmdbapp2

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun description(): String {
        return "Hi there, some thing from"
    }
}