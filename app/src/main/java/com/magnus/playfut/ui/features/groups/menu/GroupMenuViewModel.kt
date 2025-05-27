package com.magnus.playfut.ui.features.groups.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.repository.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.RemoteGroupRepository
import com.magnus.playfut.ui.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GroupMenuViewModel(
    private val remoteRepository: RemoteGroupRepository,
    private val localRepository: LocalGroupRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Group>>(UiState.Loading)
    val uiState: StateFlow<UiState<Group>> = _uiState

    fun observeGroup(groupId: String) {
        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        viewModelScope.launch {
            repo.observeGroup(groupId)
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