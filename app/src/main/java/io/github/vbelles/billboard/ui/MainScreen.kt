package io.github.vbelles.billboard.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.vbelles.billboard.R
import io.github.vbelles.billboard.ui.screens.MoviesScreen
import io.github.vbelles.billboard.ui.screens.MoviesScreenPreview
import io.github.vbelles.billboard.ui.screens.TvScreen
import io.github.vbelles.billboard.ui.screens.WatchlistScreen

data class NavigationItem(val screen: Screen, val titleRes: Int, val iconRes: Int)

@Composable
fun MainScreen(
    body: @Composable (NavHostController, Modifier) -> Unit = { navController, modifier ->
        NavigationComponent(navController, modifier)
    }
) {
    val navController = rememberNavController()
    val items = remember {
        listOf(
            NavigationItem(Screen.Movies, R.string.movies_title, R.drawable.ic_movies),
            NavigationItem(Screen.Tv, R.string.tv_shows_title, R.drawable.ic_tv_shows),
            NavigationItem(Screen.Watchlist, R.string.people_title, R.drawable.ic_people),
        )
    }
    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colors.surface) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(painterResource(item.iconRes), stringResource(item.titleRes)) },
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
    ) { innerPadding -> body(navController, Modifier.padding(innerPadding)) }
}

@Composable
fun NavigationComponent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Screen.Movies.route, modifier) {
        composable(Screen.Movies.route) { MoviesScreen(navController) }
        composable(Screen.Tv.route) { TvScreen(navController) }
        composable(Screen.Watchlist.route) { WatchlistScreen(navController) }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen { _, _ -> MoviesScreenPreview() }
}