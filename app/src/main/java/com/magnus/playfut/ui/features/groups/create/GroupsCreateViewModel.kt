package com.magnus.playfut.ui.features.groups.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.repository.GroupsRepository
import com.magnus.playfut.ui.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupsCreateViewModel (
    private val repository: GroupsRepository
) : ViewModel() {

    private val _createGroupResult = MutableStateFlow<ActionResultState>(ActionResultState.Idle)
    val createGroupResult: StateFlow<ActionResultState> = _createGroupResult

    fun createGroup(name: String) {
        _createGroupResult.value = ActionResultState.Loading
        viewModelScope.launch {
            repository.createGroup(name)
                .onSuccess { _createGroupResult.value = ActionResultState.Success }
                .onFailure { _createGroupResult.value = ActionResultState.Error  }
        }
    }
}