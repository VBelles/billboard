package io.github.vbelles.billboard.ui.screens.page

import io.github.vbelles.billboard.data.model.Content


data class StripState(
    val isLoading: Boolean = false,
    val source: String,
    val title: String,
    val contents: List<Content> = emptyList(),
)

data class PageState(
    val isLoading: Boolean = false,
    val strips: List<StripState> = emptyList(),
)