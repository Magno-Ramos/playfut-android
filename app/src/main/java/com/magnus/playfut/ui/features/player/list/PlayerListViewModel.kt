package com.magnus.playfut.ui.features.player.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.repository.GroupRepository
import com.magnus.playfut.ui.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PlayerListViewModel(
    private val repository: GroupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Group>>(UiState.Loading)
    val uiState: StateFlow<UiState<Group>> = _uiState

    fun observeGroup(groupId: String) {
        viewModelScope.launch {
            repository.observeGroup(groupId)
                .onStart { _uiState.value = UiState.Loading }
                .catch { _uiState.value = UiState.Error(it.message ?: "Unknown error") }
                .collect { group ->
                    if (group != null) {
                        _uiState.value = UiState.Success(group)
                    } else {
                        _uiState.value = UiState.Error("Group not found")
                    }
                }
        }
    }
}