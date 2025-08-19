package com.example.simpletmdbapp2.android.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.simpletmdbapp2.android.helper.NetworkConnectionState


@Composable
fun NetworkStateView(
    state: NetworkConnectionState,
    modifier: Modifier = Modifier
) {
    Row(Modifier.fillMaxWidth()) {
        if (state == NetworkConnectionState.Unavailable) {
            Column(Modifier.fillMaxWidth().background(Color.Red),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No Connection",
                    color = Color.White)
            }
        }
    }
}