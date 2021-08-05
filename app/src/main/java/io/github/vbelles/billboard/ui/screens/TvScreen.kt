package io.github.vbelles.billboard.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun TvScreen(navController: NavController) {
    LazyColumn {
        item {
            Text(text = "Tv")
        }
        items(200) {
            Text(text = "Item $it")
        }
    }
}