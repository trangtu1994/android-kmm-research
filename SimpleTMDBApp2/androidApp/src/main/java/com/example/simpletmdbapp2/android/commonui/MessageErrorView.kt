package com.example.simpletmdbapp2.android.commonui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.simpletmdbapp2.android.base.ErrorDataState
import com.example.simpletmdbapp2.kotinext.safe

@Composable
fun MessageErrorContent(state: ErrorDataState) {
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().height(200.dp).padding(8.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = state.message,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
        if (state.description.safe().isNotBlank()) {
            Spacer(Modifier.size(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = state.description.safe(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
            )
        }
    }
}