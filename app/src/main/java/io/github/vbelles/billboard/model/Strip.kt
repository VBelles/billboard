package io.github.vbelles.billboard.model

data class Strip(
    val title: String,
    val subtitle: String,
    val nodes: List<ContentNode>,
)