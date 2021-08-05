package io.github.vbelles.billboard

sealed class Screen(val route: String) {
    object Movies : Screen("movies")
    object Tv : Screen("tv")
    object Watchlist : Screen("watchlist")
}