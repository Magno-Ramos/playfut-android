package com.magnus.playfut.ui.features.groups.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.model.relations.GroupWithOpenedRound
import com.magnus.playfut.domain.repository.GroupRepository
import com.magnus.playfut.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupMenuViewModel(
    private val repository: GroupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<GroupWithOpenedRound>>(UiState.Loading)
    val uiState: StateFlow<UiState<GroupWithOpenedRound>> = _uiState

    fun fetchGroup(groupId: String) {
        viewModelScope.launch {
            repository.getGroupWithOpenedRound(groupId)
                .onFailure { _uiState.value = UiState.Error(exception = it) }
                .onSuccess { group -> _uiState.value = UiState.Success(group) }
        }
    }
}