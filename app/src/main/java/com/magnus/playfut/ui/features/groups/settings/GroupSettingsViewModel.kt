package com.magnus.playfut.ui.features.groups.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.repository.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.RemoteGroupRepository
import com.magnus.playfut.ui.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupSettingsViewModel(
    private val remoteRepo: RemoteGroupRepository,
    private val localRepo: LocalGroupRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _deleteGroupResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val deleteGroupResult: StateFlow<ActionResultState<Unit>> = _deleteGroupResult

    fun deleteGroup(groupId: String) {
        _deleteGroupResult.value = ActionResultState.Loading

        val repo = if (auth.currentUser != null) remoteRepo else localRepo

        viewModelScope.launch {
            repo.deleteGroup(groupId)
                .onSuccess { _deleteGroupResult.value = ActionResultState.Success(Unit) }
                .onFailure { _deleteGroupResult.value = ActionResultState.Error(null) }
        }
    }
}