package com.example.simpletmdbapp2.android.commonui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    isInRoot: Boolean,
    onBackClick: (() -> Unit)
) {
    TopAppBar(
        title = {
            Text( title )
        },
        navigationIcon = {
            if (!isInRoot) {
                IconButton(onClick = { onBackClick.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
    )

}
