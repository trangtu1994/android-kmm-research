package com.example.simpletmdbapp2.android.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Date.toString(format: String): String? {
    return SimpleDateFormat(format).format(this)
}
