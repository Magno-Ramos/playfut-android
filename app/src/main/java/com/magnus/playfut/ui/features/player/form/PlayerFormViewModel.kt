package com.magnus.playfut.ui.features.player.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.domain.repository.LocalPlayerRepository
import com.magnus.playfut.ui.domain.repository.RemotePlayerRepository
import com.magnus.playfut.ui.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerFormViewModel(
    private val remoteRepository: RemotePlayerRepository,
    private val localRepository: LocalPlayerRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _createPlayerResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val createPlayerResult: StateFlow<ActionResultState<Unit>> = _createPlayerResult

    private val _editPlayerResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val editPlayerResult: StateFlow<ActionResultState<Unit>> = _editPlayerResult

    private val _deletePlayerResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val deletePlayerResult: StateFlow<ActionResultState<Unit>> = _deletePlayerResult

    fun createPlayer(groupId: String, name: String, type: PlayerType, quality: Int) {
        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        viewModelScope.launch {
            _createPlayerResult.value = ActionResultState.Loading
            repo.createPlayer(groupId, name, type, quality)
                .onSuccess { _createPlayerResult.value = ActionResultState.Success(Unit) }
                .onFailure { _createPlayerResult.value = ActionResultState.Error(null) }
        }
    }

    fun editPlayer(id: String, groupId: String, name: String, type: PlayerType, quality: Int) {
        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        viewModelScope.launch {
            _editPlayerResult.value = ActionResultState.Loading
            repo.editPlayer(id, groupId, name, type, quality)
                .onSuccess { _editPlayerResult.value = ActionResultState.Success(Unit) }
                .onFailure { _editPlayerResult.value = ActionResultState.Error(null) }
        }
    }

    fun deletePlayer(id: String) {
        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        viewModelScope.launch {
            _deletePlayerResult.value = ActionResultState.Loading
            repo.deletePlayer(id)
                .onSuccess { _deletePlayerResult.value = ActionResultState.Success(Unit) }
                .onFailure { _deletePlayerResult.value = ActionResultState.Error(null) }
        }
    }
}