package com.magnus.playfut.ui.features.groups.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.repository.GroupRepository
import com.magnus.playfut.domain.state.ActionResultState
import com.magnus.playfut.domain.state.ActionResultState.Error
import com.magnus.playfut.domain.state.ActionResultState.Idle
import com.magnus.playfut.domain.state.ActionResultState.Loading
import com.magnus.playfut.domain.state.ActionResultState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupsFormViewModel(
    private val repository: GroupRepository
) : ViewModel() {

    private val _createGroupResult = MutableStateFlow<ActionResultState<String>>(Idle)
    val createGroupResult: StateFlow<ActionResultState<String>> = _createGroupResult

    private val _editGroupResult = MutableStateFlow<ActionResultState<Unit>>(Idle)
    val editGroupResult: StateFlow<ActionResultState<Unit>> = _editGroupResult

    fun createGroup(name: String) {
        _createGroupResult.value = Loading
        viewModelScope.launch {
            repository.createGroup(name.trim())
                .onSuccess { _createGroupResult.value = Success(it) }
                .onFailure { _createGroupResult.value = Error(null) }
        }
    }

    fun editGroup(id: String, name: String) {
        _editGroupResult.value = Loading
        viewModelScope.launch {
            repository.editGroup(id, name.trim())
                .onSuccess { _editGroupResult.value = Success(it) }
                .onFailure { _editGroupResult.value = Error(null) }
        }
    }
}