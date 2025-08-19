package com.example.simpletmdbapp2.android.feature.movie

import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.simpletmdbapp2.android.commonui.UrlImage
import com.example.simpletmdbapp2.android.extension.toDate
import com.example.simpletmdbapp2.android.extension.toString
import com.example.simpletmdbapp2.android.helper.CONFIG_DATE_FORMAT
import com.example.simpletmdbapp2.kotinext.safe
import com.example.simpletmdbapp2.model.Movie

@Composable
fun MovieGridItem2(modifier: Modifier = Modifier, movie: Movie, onMovieClick: (Movie) -> Unit) {
    Card(Modifier.padding(2.dp).height(200.dp)) {
        Column(Modifier
            .fillMaxHeight()
            .clickable(true) {
                onMovieClick.invoke(movie)
            }.padding(2.dp)) {
            Box(modifier = Modifier.fillMaxSize()) {
                MovieItemBackdrop(backdropPath = movie.backdrop.safe())
                Column(modifier = Modifier.fillMaxSize().offset(0.dp, 50.dp).padding(horizontal = 5.dp)) {
                    UrlImage(url = movie.poster,
                        Size(150, 225),"Movie poster",
                        modifier = Modifier.width(70.dp).height(80.dp)
                            .clip(RoundedCornerShape(8.dp)))
                    Spacer(Modifier.size(5.dp))
                    Text(text = movie.name,
                        maxLines = 2,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(0.95f))
                    Text(text = "${movie.year.toDate("yyyy-MM-dd")?.toString(CONFIG_DATE_FORMAT)}  |  Vote: ${"%.1f".format(movie.vote)}",
                        maxLines = 2,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun MovieItemBackdrop(backdropPath: String) {
    val imageUrl = "https://image.tmdb.org/t/p/original$backdropPath"

    Box(modifier = Modifier.fillMaxSize()) {
        UrlImage(imageUrl, Size(180, 180),"Movie Backdrop",
            modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.05f), Color.Black.copy(alpha = 0.95f)),
                    startY = 0f,
                    endY = 300f
                )
                )
        )
    }
}
