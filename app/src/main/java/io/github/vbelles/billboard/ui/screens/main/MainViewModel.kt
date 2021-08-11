package io.github.vbelles.billboard.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vbelles.billboard.data.repository.section.SectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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