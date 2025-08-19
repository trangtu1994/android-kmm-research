package com.example.simpletmdbapp2

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.simpletmdbapp2.cache.DatabaseDriverFactory
import com.jetbrains.tmbdmovies.cache.AppDatabase

class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "movies.db")
    }
}

