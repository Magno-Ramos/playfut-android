package com.magnus.playfut.ui.features.home

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.repository.local.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteGroupRepository
import com.magnus.playfut.ui.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val roomRepo: LocalGroupRepository,
    private val remoteRepo: RemoteGroupRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Group>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Group>>> = _uiState

    init {
        observeLoginState()
    }

    private fun observeLoginState() {
        viewModelScope.launch {
            snapshotFlow { auth.currentUser }
                .collectLatest { user ->
                    val repo = if (user != null) remoteRepo else roomRepo
                    repo.observeGroups()
                        .onStart { _uiState.value = UiState.Loading }
                        .catch { _uiState.value = UiState.Error(it.message ?: "Unknown error") }
                        .collect { groups -> _uiState.value = UiState.Success(groups) }
                }
        }
    }
}