package com.example.simpletmdbapp2.android.helper

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.simpletmdbapp2.android.helper.AppNavArgName.ITEM_ID_ARG

interface Route {
    val id: String
    val title: String
}

object TrendingRoute : Route {

    override val id: String
        get() = "trending"
    override val title: String
        get() = "Trending Movies"

}

object SearchRoute : Route {
    override val id: String
        get() = "search"
    override val title: String
        get() = "Search Movies"

}

object DetailRoute : Route {
    override val id: String
        get() = "detail"
    override val title: String
        get() = "Detail Movie"
    const val navArg = ITEM_ID_ARG
    val routeIdWithArg = "$id/{$navArg}"
    val arguments = listOf(
        navArgument(navArg) {type = NavType.LongType}
    )
}

val appNavRoutes = listOf(TrendingRoute, SearchRoute, DetailRoute)

