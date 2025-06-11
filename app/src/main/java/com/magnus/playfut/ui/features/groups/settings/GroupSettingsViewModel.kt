package com.magnus.playfut.ui.features.groups.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.repository.GroupRepository
import com.magnus.playfut.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupSettingsViewModel(
    private val repository: GroupRepository
) : ViewModel() {

    private val _deleteGroupResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val deleteGroupResult: StateFlow<ActionResultState<Unit>> = _deleteGroupResult

    fun deleteGroup(groupId: String) {
        _deleteGroupResult.value = ActionResultState.Loading
        viewModelScope.launch {
            repository.deleteGroup(groupId)
                .onSuccess { _deleteGroupResult.value = ActionResultState.Success(Unit) }
                .onFailure { _deleteGroupResult.value = ActionResultState.Error(null) }
        }
    }
}