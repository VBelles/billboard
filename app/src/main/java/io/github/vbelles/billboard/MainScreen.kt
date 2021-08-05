package io.github.vbelles.billboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.vbelles.billboard.ui.screens.MoviesScreen
import io.github.vbelles.billboard.ui.screens.TvScreen
import io.github.vbelles.billboard.ui.screens.WatchlistScreen

data class NavigationItem(val screen: Screen, val titleRes: Int, val icon: ImageVector)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = remember {
        listOf(
            NavigationItem(Screen.Movies, R.string.movies_title, Icons.Rounded.Place),
            NavigationItem(Screen.Tv, R.string.tv_title, Icons.Rounded.List),
            NavigationItem(Screen.Watchlist, R.string.watchlist_title, Icons.Rounded.Favorite),
        )
    }
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(item.icon, stringResource(item.titleRes)) },
                        label = { Text(stringResource(item.titleRes)) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(0) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Movies.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Movies.route) { MoviesScreen(navController) }
            composable(Screen.Tv.route) { TvScreen(navController) }
            composable(Screen.Watchlist.route) { WatchlistScreen(navController) }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}