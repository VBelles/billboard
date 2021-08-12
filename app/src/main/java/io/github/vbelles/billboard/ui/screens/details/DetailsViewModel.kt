package io.github.vbelles.billboard.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vbelles.billboard.data.model.ContentType
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(private val contentRepository: ContentRepository, private val id: Int, private val contentType: ContentType) :
    ViewModel() {

    private val _state = MutableStateFlow(DetailsState(isLoading = true))
    val state: StateFlow<DetailsState> = _state

    init {
        viewModelScope.launch {
            contentRepository.findContent(id, contentType).onSuccess { content ->
                _state.update {
                    DetailsState(
                        title = content.title,
                        backdrop = content.backdropPath,
                        poster = content.backdropPath,
                        description = content.overview
                    )
                }
            }
        }
    }

}