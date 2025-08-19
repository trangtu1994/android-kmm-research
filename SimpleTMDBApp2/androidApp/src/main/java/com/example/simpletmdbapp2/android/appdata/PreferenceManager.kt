package com.example.simpletmdbapp2.android.appdata

import android.content.Context
import com.example.simpletmdbapp2.data.service.SharePreferenceManager

class PreferenceManager(context: Context): SharePreferenceManager {

    private val sharePreference = context.getSharedPreferences("AppSharedPref",Context.MODE_PRIVATE)

    override fun save(key: String, data: Any) {
        when(data) {
            is Long -> {
                with((sharePreference.edit())) {
                    putLong(key, data)
                    apply()
                }
            }
            is String -> {
                with((sharePreference.edit())) {
                    putString(key, data)
                    apply()
                }
            }
            is Int -> {
                with((sharePreference.edit())) {
                    putInt(key, data)
                    apply()
                }
            }
        }
    }

    override fun getString(key: String): String? {
        return sharePreference.getString(key, null)
    }

    override fun getInt(key: String): Int {
        return sharePreference.getInt(key, 0)
    }

    override fun getLong(key: String): Long {
        return sharePreference.getLong(key, 0)
    }
}