package com.example.simpletmdbapp2.android

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpletmdbapp2.android.feature.activity.MainViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<MainViewModel>()
    val state by remember { viewModel.state }
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
}