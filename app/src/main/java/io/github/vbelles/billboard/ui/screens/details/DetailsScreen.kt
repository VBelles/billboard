package io.github.vbelles.billboard.ui.screens.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(navController: NavController, id: String) {
    val viewModel: DetailsViewModel = getViewModel { parametersOf(id) }
    Text(text = id)
}