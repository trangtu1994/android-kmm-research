package com.example.simpletmdbapp2.android.commonui

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UrlImage(url: String?,
             size: android.util.Size,
             content: String = "",
             contentScale: ContentScale = ContentScale.Crop,
             modifier: Modifier = Modifier
) {
    Log.i("IMAGE_LOADER", url ?: "NONE")
    GlideImage(model = url,
        contentDescription = content,
        modifier = modifier.height(size.width.dp).width(size.height.dp),
        alignment = Alignment.TopCenter,
        contentScale = contentScale
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UrlCircleImage(url: String?,
                   size: android.util.Size,
                   content: String = "",
                   contentScale: ContentScale = ContentScale.Crop,
                   modifier: Modifier = Modifier
) {

    GlideImage(model = url,
        contentDescription = content,
        modifier = modifier.height(size.width.dp)
            .width(size.height.dp).clip(CircleShape),
        contentScale = contentScale,
        alignment = Alignment.TopCenter
    )
}