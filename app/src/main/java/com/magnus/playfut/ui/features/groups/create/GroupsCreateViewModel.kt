package com.magnus.playfut.ui.features.groups.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.repository.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.RemoteGroupRepository
import com.magnus.playfut.ui.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GroupsCreateViewModel(
    private val remoteRepository: RemoteGroupRepository,
    private val localRepository: LocalGroupRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _createGroupResult = MutableStateFlow<ActionResultState>(ActionResultState.Idle)
    val createGroupResult: StateFlow<ActionResultState> = _createGroupResult

    fun createGroup(name: String) {
        _createGroupResult.value = ActionResultState.Loading

        val repo = if (auth.currentUser != null) remoteRepository else localRepository

        viewModelScope.launch {
            repo.createGroup(name)
                .onSuccess {
                    _createGroupResult.value = ActionResultState.Success
                }
                .onFailure {
                    _createGroupResult.value = ActionResultState.Error(null)
                }
        }
    }
}