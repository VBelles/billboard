package io.github.vbelles.billboard.ui.screens.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vbelles.billboard.data.model.Content
import io.github.vbelles.billboard.data.model.Section
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import io.github.vbelles.billboard.data.repository.section.SectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PageViewModel(
    sectionId: String,
    private val title: String,
    private val contentRepository: ContentRepository,
    private val sectionRepository: SectionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        PageState(
            isLoading = true,
            title = title,
        )
    )
    val state: StateFlow<PageState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            sectionRepository.getSection(sectionId)
                .onSuccess { section -> onSectionLoaded(section) }
                .onFailure { println("Failure $it") }
        }
    }

    private fun onSectionLoaded(section: Section) {
        _state.update {
            PageState(
                title = title,
                strips = section.strips.map { strip ->
                    StripState(
                        isLoading = true,
                        source = strip.source,
                        title = strip.title,
                        contentType = strip.contentType,
                    )
                })
        }
    }

    fun loadStrip(stripState: StripState) {
        viewModelScope.launch {
            contentRepository.listContents(stripState.source, stripState.contentType)
                .onSuccess { contents -> onStripLoaded(stripState.source, contents) }
                .onFailure { onStripError(stripState.source) }
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