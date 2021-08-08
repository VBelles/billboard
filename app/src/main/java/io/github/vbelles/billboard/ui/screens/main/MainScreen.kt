package io.github.vbelles.billboard.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.vbelles.billboard.data.model.Section
import io.github.vbelles.billboard.ui.screens.PeopleScreen
import io.github.vbelles.billboard.ui.screens.page.PageScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    MainScreen(state)
}

@Composable
fun NavigationComponent(sections: List<Section>, navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = sections.first().name, modifier) {
        sections.forEach { section ->
            composable(section.name) {
                when (section.sectionType) {
                    Section.SectionType.Page -> PageScreen(navController, section)
                    Section.SectionType.People -> PeopleScreen(navController, section.name)
                }
            }
        }
    }
}


@Composable
fun MainScreen(state: SectionsState) {
    val navController = rememberNavController()

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val navigationBarColor = MaterialTheme.colors.surface

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = useDarkIcons)
        systemUiController.setNavigationBarColor(color = navigationBarColor, darkIcons = useDarkIcons)
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colors.surface, modifier = Modifier.navigationBarsPadding()) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                state.sections.forEach { section ->
                    BottomNavigationItem(
                        icon = { Icon(painterResource(section.icon), section.name) },
                        label = { Text(section.name) },
                        selected = section.name == navBackStackEntry?.destination?.route,
                        onClick = {
                            navController.navigate(section.name) {
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
        if (state.sections.isNotEmpty()) {
            NavigationComponent(state.sections, navController, Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun painterResource(name: String): Painter {
    val context = LocalContext.current
    return painterResource(context.resources.getIdentifier(name, "drawable", context.packageName))
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(SectionsState())
}