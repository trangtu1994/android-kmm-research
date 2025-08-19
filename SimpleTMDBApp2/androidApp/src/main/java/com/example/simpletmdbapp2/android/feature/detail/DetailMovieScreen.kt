package com.example.simpletmdbapp2.android.feature.detail


import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simpletmdbapp2.android.commonui.UrlImage
import com.example.simpletmdbapp2.kotinext.safe
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMovieScreen(
    viewModel: DetailScreenViewModel = koinViewModel()) {

    val stateItem = viewModel.state.collectAsStateWithLifecycle()
    val pageState = viewModel.pageState.collectAsStateWithLifecycle()

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = pageState.value.loading,
        onRefresh = {viewModel.refreshMovieDetail()},
    ) {
        when(stateItem.value.movieDetail) {
            is ErrorDetailState -> {
                MovieContentErrorContent(stateItem.value.movieDetail as ErrorDetailState)
            }
            is MovieDetailState -> {
                MovieDetailContent(stateItem.value.movieDetail as MovieDetailState)
            }
        }
    }
}

@Composable
fun MovieContentErrorContent(state: ErrorDetailState) {
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().height(200.dp).padding(8.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = state.message ?: "Unknown error!",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
        if (state.description.safe().isNotBlank()) {
            Spacer(Modifier.size(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left,
                text = state.description.safe(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
            )
        }
    }
}

@Composable
fun MovieDetailContent(detail: MovieDetailState) {
    val scrollState = rememberScrollState()
    val imageUrl = "https://image.tmdb.org/t/p/w500${detail.posterPath}"

    Column(Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(scrollState)) {
        Box(modifier = Modifier.fillMaxSize()) {
            MovieBackdrop(backdropPath = "https://image.tmdb.org/t/p/w500${detail.backDropPath}")
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 160.dp)
            ) {
                Row(Modifier.fillMaxSize()) {
                    UrlImage(imageUrl,
                        Size(150, 225),"Movie poster",
                        modifier = Modifier
                            .width(120.dp) // Set a fixed width for the poster
                            .height(225.dp) // Maintain a standard poster aspect ratio
                            .clip(RoundedCornerShape(8.dp)) // Add rounded corners
                            .fillMaxHeight())

                    Column (
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 100.dp)
                    ) {
                        Text(
                            text = detail.name.safe(),
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                        Text(
                            text = "${detail.releaseDate}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
                Text(
                    text = "${detail.description}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(Modifier.size(8.dp))
                detail.otherData.forEach {
                    MovieAddRowItem(it)
                }
            }
        }
    }
}

@Composable
fun MovieBackdrop(backdropPath: String) {
    val imageUrl = "https://image.tmdb.org/t/p/original$backdropPath"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Set a fixed height for the backdrop
    ) {
        UrlImage(imageUrl, Size(250, 250),"Movie Backdrop",
            modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                    startY = 0f,
                    endY = 1000f
                )
                )
        )
    }
}

@Composable
fun MovieAddRowItem(dataState: MovieDataState) {
    Row(Modifier.fillMaxSize().padding(vertical = 2.dp)) {
        Text(
            text = "- ${dataState.key}:",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.widthIn(min = 100.dp)
        )
        Text(
            text = dataState.stringValue.safe(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}