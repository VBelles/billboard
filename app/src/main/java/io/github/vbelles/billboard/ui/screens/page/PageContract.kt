package io.github.vbelles.billboard.ui.screens.page

import io.github.vbelles.billboard.data.model.Content


data class StripState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val source: String,
    val title: String,
    val contents: List<Content> = emptyList(),
)

data class PageState(
    val title: String,
    val strips: List<StripState> = emptyList(),
)