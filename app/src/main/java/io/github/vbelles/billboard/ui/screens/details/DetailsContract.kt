package io.github.vbelles.billboard.ui.screens.details


sealed interface DetailsAction {
    object BackClicked : DetailsAction
}

data class DetailsState(
    val isLoading: Boolean = false,
    val title: String? = null,
    val backdrop: String? = null,
    val poster: String? = null,
    val description: String? = null,
)