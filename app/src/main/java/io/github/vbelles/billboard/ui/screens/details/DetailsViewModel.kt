package io.github.vbelles.billboard.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(private val contentRepository: ContentRepository, private val id: Int) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState(isLoading = true))
    val state: StateFlow<DetailsState> = _state

    init {
        viewModelScope.launch {
            contentRepository.findContent(id).also { println(it) }.onSuccess { content ->
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