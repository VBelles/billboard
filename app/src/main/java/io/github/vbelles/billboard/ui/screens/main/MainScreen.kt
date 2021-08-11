package io.github.vbelles.billboard.ui.screens.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.vbelles.billboard.R
import io.github.vbelles.billboard.ui.Screen

data class ScreenNavigationItem(val screen: Screen, val iconRes: Int, val titleRes: Int)


@Composable
fun MainScreen() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val bottomAppBarColor = MaterialTheme.colors.surface.copy(alpha = 0.95f)

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = useDarkIcons)
        systemUiController.setNavigationBarColor(color = bottomAppBarColor, darkIcons = useDarkIcons)
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val items = remember {
        listOf(
            ScreenNavigationItem(Screen.Movies, R.drawable.ic_movies, R.string.movies_title),
            ScreenNavigationItem(Screen.TvShows, R.drawable.ic_tv_shows, R.string.tv_shows_title),
            ScreenNavigationItem(Screen.Movies, R.drawable.ic_people, R.string.people_title),
        )
    }

    val isRootScreen = items.any { item -> navBackStackEntry?.destination?.route == item.screen.route }

    Scaffold(
        bottomBar = {
            if (isRootScreen) {
                BottomBar(navController, bottomAppBarColor, items)
            }
        }
    ) { innerPadding ->
        NavigationComponent(navController, innerPadding)
    }
}

@Composable
fun BottomBar(navController: NavController, color: Color, navigationItems: List<ScreenNavigationItem>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    BottomNavigation(
        backgroundColor = color,
        modifier = Modifier.navigationBarsPadding()
    ) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(item.iconRes), null) },
                label = { Text(stringResource(item.titleRes)) },
                selected = item.screen.route == navBackStackEntry?.destination?.route,
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

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}