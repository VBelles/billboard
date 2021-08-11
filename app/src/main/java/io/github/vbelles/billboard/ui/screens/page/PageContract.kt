package io.github.vbelles.billboard.ui.screens.page

import io.github.vbelles.billboard.data.model.Content
import io.github.vbelles.billboard.data.model.ContentType


sealed interface PageAction {
    data class StripDisplayed(val source: String, val contentType: ContentType) : PageAction
    data class ViewMoreClicked(val source: String, val contentType: ContentType) : PageAction
    data class ContentClicked(val contentId: Int, val contentType: ContentType) : PageAction
}

data class StripState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val source: String,
    val contentType: ContentType,
    val title: String,
    val contents: List<Content> = emptyList(),
)

data class PageState(
    val isLoading: Boolean = false,
    val title: String,
    val strips: List<StripState> = emptyList(),
)