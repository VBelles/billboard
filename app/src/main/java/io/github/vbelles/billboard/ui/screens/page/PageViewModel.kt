package io.github.vbelles.billboard.ui.screens.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vbelles.billboard.data.model.Strip
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PageViewModel(private val contentRepository: ContentRepository) : ViewModel() {


    private val _state = MutableStateFlow(PageState())
    val state: StateFlow<PageState> = _state.asStateFlow()

    fun start(strips: List<Strip>) {
        if (_state.value.strips.isEmpty()) {
            _state.update { it.copy(strips = strips.map { strip -> StripState(source = strip.source, title = strip.name) }) }
        }
    }

    fun loadStrip(source: String) {
        viewModelScope.launch {
            contentRepository.listContents(source)
                .onSuccess { contents ->
                    _state.update {
                        it.copy(strips = it.strips.map { strip ->
                            when (strip.source) {
                                source -> strip.copy(contents = contents)
                                else -> strip
                            }
                        })
                    }
                }
        }
    }


}