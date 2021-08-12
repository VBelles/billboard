package io.github.vbelles.billboard.ui.screens.main

import androidx.lifecycle.ViewModel
import io.github.vbelles.billboard.data.repository.section.SectionRepository

class MainViewModel(private val sectionRepository: SectionRepository) : ViewModel() {

    /*private val _state = MutableStateFlow(SectionsState(isLoading = true))
    val state: StateFlow<SectionsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val result = sectionRepository.listSections()
            if (result.isSuccess) {
                _state.update { SectionsState(sections = result.getOrThrow()) }
            }
        }
    }*/

}