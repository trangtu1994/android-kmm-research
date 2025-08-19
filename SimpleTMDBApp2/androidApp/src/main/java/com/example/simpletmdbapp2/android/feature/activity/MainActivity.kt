package com.example.simpletmdbapp2.android.feature.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.simpletmdbapp2.android.MyApplicationTheme
import com.example.simpletmdbapp2.android.commonui.AppTopBar
import com.example.simpletmdbapp2.android.commonui.NetworkStateView
import com.example.simpletmdbapp2.android.feature.detail.DetailMovieScreen
import com.example.simpletmdbapp2.android.feature.movie.MoviesScreen
import com.example.simpletmdbapp2.android.helper.DetailRoute
import com.example.simpletmdbapp2.android.helper.TrendingRoute
import com.example.simpletmdbapp2.android.helper.rememberConnectivityState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val connectionState by rememberConnectivityState()
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            AppTopBar(title = viewModel.navRouteTile(currentBackStack?.destination?.route),
                                currentBackStack?.destination?.route == TrendingRoute.id) {
                                navController.popBackStack()
                            }
                        }
                    ) { innerPadding ->
                        Column(Modifier.fillMaxWidth()) {
                            NetworkStateView(connectionState)
                            NavHost(
                                navController = navController,
                                startDestination = TrendingRoute.id,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable(TrendingRoute.id) {
                                    MoviesScreen(viewModel = viewModel,
                                        onMovieClick = {
                                            navController.navigateMovieDetailTop(it.id)
                                        }
                                    )
                                }
                                composable(route = DetailRoute.routeIdWithArg,
                                    arguments = DetailRoute.arguments,) {
                                    DetailMovieScreen()
                                }
                                composable(
                                    route = DetailRoute.routeIdWithArg,
                                    arguments = DetailRoute.arguments,
                                    deepLinks = listOf(navDeepLink { uriPattern = "tmdb://movie/{${DetailRoute.navArg}}" })
                                ) { backStackEntry ->
                                    DetailMovieScreen()
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

fun NavHostController.navigateSingleTop(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTop.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateMovieDetailTop(movieId: Long) =
    this.navigateSingleTop("${DetailRoute.id}/$movieId")
