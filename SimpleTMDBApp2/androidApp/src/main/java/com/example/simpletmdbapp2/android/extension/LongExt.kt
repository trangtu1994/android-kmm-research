package com.example.simpletmdbapp2.android.extension

import java.text.NumberFormat
import java.util.Locale


fun Long?.formatAmount(locale: Locale = Locale.US): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(this)
}
