package com.magnus.playfut.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.model.relations.GroupWithPlayersAndRoundsCount
import com.magnus.playfut.domain.repository.GroupRepository
import com.magnus.playfut.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: GroupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GroupWithPlayersAndRoundsCount>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<GroupWithPlayersAndRoundsCount>>> = _uiState

    fun fetchGroups() {
        viewModelScope.launch {
            repository.getAllGroups()
                .onFailure { _uiState.value = UiState.Error() }
                .onSuccess { groups -> _uiState.value = UiState.Success(groups) }
        }
    }
}