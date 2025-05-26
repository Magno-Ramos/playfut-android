package com.magnus.playfut.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.repository.GroupsRepository
import com.magnus.playfut.ui.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val groupsRepository: GroupsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Group>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Group>>> = _uiState

    fun fetchGroups() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching {
                _uiState.value = UiState.Success(groupsRepository.fetchGroups())
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Unknown error")
            }
        }
    }
}