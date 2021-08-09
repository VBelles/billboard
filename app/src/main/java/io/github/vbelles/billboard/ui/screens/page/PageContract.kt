package io.github.vbelles.billboard.ui.screens.page

import io.github.vbelles.billboard.data.model.Content


sealed interface PageAction {
    data class StripDisplayed(val source: String) : PageAction
    data class ViewMoreClicked(val source: String) : PageAction
    data class ContentClicked(val id: Int) : PageAction
}

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