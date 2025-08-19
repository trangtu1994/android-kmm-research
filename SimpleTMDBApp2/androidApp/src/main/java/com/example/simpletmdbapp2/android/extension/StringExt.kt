package com.example.simpletmdbapp2.android.extension

import java.text.SimpleDateFormat
import java.util.Date

fun String?.toDate(format: String): Date? {
    this ?: return null
    val formatter = SimpleDateFormat(format)
    return try {
        formatter.parse(this)
    } catch (e: Exception) {
        return null
    }
}
