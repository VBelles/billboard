package io.github.vbelles.billboard.data.model

data class Page(
    val id: Int,
    val name: String,
    val icon: String,
    val strips: List<Strip>,
)