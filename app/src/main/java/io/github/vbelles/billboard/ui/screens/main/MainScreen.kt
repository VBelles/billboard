package io.github.vbelles.billboard.ui.screens.main

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.vbelles.billboard.data.model.Section
import io.github.vbelles.billboard.ui.screens.PeopleScreen
import io.github.vbelles.billboard.ui.screens.details.DetailsScreen
import io.github.vbelles.billboard.ui.screens.grid.GridScreen
import io.github.vbelles.billboard.ui.screens.page.PageScreen
import org.koin.androidx.compose.getViewModel
import java.net.URLDecoder

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    MainScreen(state)
}

@Composable
fun NavigationComponent(sections: List<Section>, navController: NavHostController, innerPadding: PaddingValues) {
    if (sections.isEmpty()) return
    NavHost(navController, startDestination = sections.first().name) {
        sections.forEach { section ->
            composable(section.name) {
                when (section.sectionType) {
                    Section.SectionType.Page -> PageScreen(navController, section, innerPadding)
                    Section.SectionType.People -> PeopleScreen(navController, section.name)
                }
            }
        }
        composable("grid/{source}") {
            val source = URLDecoder.decode(it.arguments?.getString("source"), "utf-8")
            GridScreen(navController, source)
        }
        composable("details/{id}") {
            DetailsScreen(navController, it.arguments?.getString("id")!!)
        }
    }
}


@Composable
fun MainScreen(state: SectionsState) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val bottomAppBarColor = MaterialTheme.colors.surface.copy(alpha = 0.95f)

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = useDarkIcons)
        systemUiController.setNavigationBarColor(color = bottomAppBarColor, darkIcons = useDarkIcons)
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isRootScreen = state.sections.any { section -> navBackStackEntry?.destination?.route == section.name }

    Scaffold(
        bottomBar = {
            if (state.sections.isNotEmpty() && isRootScreen) {
                BottomBar(navController, bottomAppBarColor, state.sections)
            }
        }
    ) { innerPadding ->
        NavigationComponent(state.sections, navController, innerPadding)
    }
}

@Composable
fun BottomBar(navController: NavController, color: Color, sections: List<Section>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    BottomNavigation(
        backgroundColor = color,
        modifier = Modifier.navigationBarsPadding()
    ) {
        sections.forEach { section ->
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