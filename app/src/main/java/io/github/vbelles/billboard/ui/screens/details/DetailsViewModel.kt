package io.github.vbelles.billboard.ui.screens.details

import androidx.lifecycle.ViewModel
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailsViewModel(private val contentRepository: ContentRepository, private val id: String) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state

}