package io.github.vbelles.billboard.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun PeopleScreen(navController: NavController, title: String?) {
    println("PeopleScreen $title")
    Text(title ?: "Undefined")
}