package io.github.vbelles.billboard.ui.screens.main

import io.github.vbelles.billboard.data.model.Section

data class SectionsState(
    val isLoading: Boolean = false,
    val sections: List<Section> = emptyList()
)