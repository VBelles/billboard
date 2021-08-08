package io.github.vbelles.billboard.ui.screens.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vbelles.billboard.data.model.Content
import io.github.vbelles.billboard.data.model.Section
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PageViewModel(private val contentRepository: ContentRepository, section: Section) : ViewModel() {

    private val _state = MutableStateFlow(
        PageState(
            title = section.name,
            strips = section.strips.map { strip ->
                StripState(
                    isLoading = true,
                    source = strip.source,
                    title = strip.name
                )
            }
        )
    )
    val state: StateFlow<PageState> = _state.asStateFlow()

    fun loadStrip(source: String) {
        viewModelScope.launch {
            contentRepository.listContents(source)
                .onSuccess { contents -> onStripLoaded(source, contents) }
                .onFailure { onStripError(source) }
        }
    }

    private fun onStripLoaded(source: String, contents: List<Content>) {
        updateStripState(source) { strip ->
            strip.copy(isError = false, isLoading = false, contents = contents)
        }
    }

    private fun onStripError(source: String) {
        updateStripState(source) { strip ->
            strip.copy(isError = strip.contents.isEmpty(), isLoading = false)
        }
    }

    private fun updateStripState(source: String, update: (StripState) -> StripState) {
        _state.update { state ->
            state.copy(strips = state.strips.map { strip ->
                when (strip.source) {
                    source -> update(strip)
                    else -> strip
                }
            })
        }
    }


}