package io.github.vbelles.billboard.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.vbelles.billboard.R
import io.github.vbelles.billboard.data.model.ContentType
import io.github.vbelles.billboard.ui.Screen
import io.github.vbelles.billboard.ui.screens.PeopleScreen
import io.github.vbelles.billboard.ui.screens.details.DetailsScreen
import io.github.vbelles.billboard.ui.screens.grid.GridScreen
import io.github.vbelles.billboard.ui.screens.page.PageScreen

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Movies.route) {
        composable(Screen.Movies.route) {
            PageScreen(navController, stringResource(R.string.movies_title), "movies")
        }
        composable(Screen.TvShows.route) {
            PageScreen(navController, stringResource(R.string.tv_shows_title), "tv")
        }
        composable(Screen.People.route) {
            PeopleScreen(navController, stringResource(R.string.people_title))
        }
        composable(Screen.Grid.route) {
            val id = it.arguments?.getString("id")!!
            val contentType = ContentType.fromId(it.arguments?.getString("content_type")!!)!!
            GridScreen(navController, id, contentType)
        }
        composable(Screen.Details.route) {
            val id = it.arguments?.getString("id")!!.toInt()
            val contentType = ContentType.fromId(it.arguments?.getString("content_type")!!)!!
            DetailsScreen(navController, id, contentType)
        }
    }
}