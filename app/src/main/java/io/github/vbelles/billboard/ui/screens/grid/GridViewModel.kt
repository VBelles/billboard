package io.github.vbelles.billboard.ui.screens.grid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GridViewModel(private val contentRepository: ContentRepository, private val source: String) : ViewModel() {

    private val _state = MutableStateFlow(GridState())
    val state: StateFlow<GridState> = _state

    fun loadMore(lastVisibleIndex: Int) {
        viewModelScope.launch {
            val nextPage = state.value.nextPage
            val isLastIndex = lastVisibleIndex >= state.value.contents.lastIndex - 5 || state.value.contents.isEmpty()
            if (isLastIndex && !state.value.isLoading && nextPage != null) {
                _state.update { state -> state.copy(isLoading = true) }
                loadPage(nextPage)
            }
        }
    }

    private suspend fun loadPage(page: Int) {
        contentRepository.listPagedContents(source, page)
            .onSuccess { pagedContents ->
                _state.update { state ->
                    state.copy(
                        contents = state.contents + pagedContents.contents,
                        isLoading = false,
                        nextPage = pagedContents.nextPage,
                    )
                }
            }
    }

}