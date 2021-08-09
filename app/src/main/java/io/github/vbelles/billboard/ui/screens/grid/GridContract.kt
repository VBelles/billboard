package io.github.vbelles.billboard.ui.screens.grid

import io.github.vbelles.billboard.data.model.Content

data class GridState(
    val isLoading: Boolean = false,
    val contents: List<Content> = emptyList(),
    val nextPage: Int? = 1
)