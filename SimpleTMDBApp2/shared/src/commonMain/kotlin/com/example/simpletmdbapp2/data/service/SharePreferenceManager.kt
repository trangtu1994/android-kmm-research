package com.example.simpletmdbapp2.data.service

interface SharePreferenceManager {

    fun save(key: String, data: Any)

    fun getString(key: String) : String?
    fun getInt(key: String) : Int
    fun getLong(key: String) : Long

}

