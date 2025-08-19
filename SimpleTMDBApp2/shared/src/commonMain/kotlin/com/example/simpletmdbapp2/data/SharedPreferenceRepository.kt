package com.example.simpletmdbapp2.data

import com.example.simpletmdbapp2.data.service.SharePreferenceManager

class SharedPreferenceRepository(
    private val manager: SharePreferenceManager
) {

    fun saveLong(key: String, long: Long) {
        manager.save(key, long)
    }

    fun saveInt(key: String, int: Int) {
        manager.save(key, int)
    }

    fun saveString(key: String, string: String) {
        manager.save(key, string)
    }

    fun getString(key: String): String? {
        return manager.getString(key)
    }

    fun getLong(key: String): Long {
        return manager.getLong(key)
    }

    fun getInt(key: String): Int {
        return manager.getInt(key)
    }

}