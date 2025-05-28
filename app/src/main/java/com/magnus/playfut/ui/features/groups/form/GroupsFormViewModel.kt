package com.magnus.playfut.ui.features.groups.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.repository.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.RemoteGroupRepository
import com.magnus.playfut.ui.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupsFormViewModel(
    private val remoteRepository: RemoteGroupRepository,
    private val localRepository: LocalGroupRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _createGroupResult = MutableStateFlow<ActionResultState<String>>(ActionResultState.Idle)
    val createGroupResult: StateFlow<ActionResultState<String>> = _createGroupResult

    private val _editGroupResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val editGroupResult: StateFlow<ActionResultState<Unit>> = _editGroupResult

    fun createGroup(name: String) {
        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        _createGroupResult.value = ActionResultState.Loading
        viewModelScope.launch {
            repo.createGroup(name)
                .onSuccess { _createGroupResult.value = ActionResultState.Success(it) }
                .onFailure { _createGroupResult.value = ActionResultState.Error(null) }
        }
    }

    fun editGroup(id: String, name: String) {
        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        _editGroupResult.value = ActionResultState.Loading
        viewModelScope.launch {
            repo.editGroup(id, name)
                .onSuccess { _editGroupResult.value = ActionResultState.Success(it) }
                .onFailure { _editGroupResult.value = ActionResultState.Error(null) }
        }
    }
}