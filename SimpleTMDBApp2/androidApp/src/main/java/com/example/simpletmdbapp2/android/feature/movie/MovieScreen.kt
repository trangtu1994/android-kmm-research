package com.example.simpletmdbapp2.android.feature.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.simpletmdbapp2.android.base.ErrorDataState
import com.example.simpletmdbapp2.android.commonui.MessageErrorContent
import com.example.simpletmdbapp2.android.commonui.SearchTextField
import com.example.simpletmdbapp2.android.feature.activity.MainViewModel
import com.example.simpletmdbapp2.kotinext.safe
import com.example.simpletmdbapp2.model.Movie


@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onMovieClick: (Movie) -> Unit
) {
    val state = viewModel.state.value

    val pageState = viewModel.pageState.collectAsState()
        Column {
            SearchTextField(
                Modifier, placeHolderText = "Search for movie...",
                text = state.searching.safe(),
                onSearchClicked = { viewModel.searchMovies(it) },
                onTextChanged = { viewModel.updateSearchTerm(it) })
            if (pageState.value.error == null) {
                MovieGrid(
                    modifier,
                    pageState.value.loading,
                    state.launches,
                    onMovieClick = onMovieClick
                ) { viewModel.onRefresh() }
            } else if (pageState.value.error is ErrorDataState) {
                Column(Modifier.fillMaxSize()) {
                    MessageErrorContent(pageState.value.error as ErrorDataState)
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGrid(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onRefresh: () -> Unit
) {

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize(),
            columns = GridCells.Fixed(2)
        ) {
            items(movies) { movie ->
                MovieGridItem2(modifier, movie, onMovieClick = { onMovieClick.invoke(movie) })
            }
        }
    }
}

