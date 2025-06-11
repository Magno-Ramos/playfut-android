package com.magnus.playfut.ui.features.player.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.model.structure.PlayerType
import com.magnus.playfut.domain.repository.PlayerRepository
import com.magnus.playfut.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerFormViewModel(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _createPlayerResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val createPlayerResult: StateFlow<ActionResultState<Unit>> = _createPlayerResult

    private val _editPlayerResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val editPlayerResult: StateFlow<ActionResultState<Unit>> = _editPlayerResult

    private val _deletePlayerResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val deletePlayerResult: StateFlow<ActionResultState<Unit>> = _deletePlayerResult

    fun createPlayer(groupId: String, name: String, type: PlayerType, quality: Int) {
        viewModelScope.launch {
            _createPlayerResult.value = ActionResultState.Loading
            repository.createPlayer(groupId, name, type, quality)
                .onSuccess { _createPlayerResult.value = ActionResultState.Success(Unit) }
                .onFailure { _createPlayerResult.value = ActionResultState.Error(null) }
        }
    }

    fun editPlayer(id: String, groupId: String, name: String, type: PlayerType, quality: Int) {
        viewModelScope.launch {
            _editPlayerResult.value = ActionResultState.Loading
            repository.editPlayer(id, groupId, name, type, quality)
                .onSuccess { _editPlayerResult.value = ActionResultState.Success(Unit) }
                .onFailure { _editPlayerResult.value = ActionResultState.Error(null) }
        }
    }

    fun deletePlayer(id: String) {
        viewModelScope.launch {
            _deletePlayerResult.value = ActionResultState.Loading
            repository.deletePlayer(id)
                .onSuccess { _deletePlayerResult.value = ActionResultState.Success(Unit) }
                .onFailure { _deletePlayerResult.value = ActionResultState.Error(null) }
        }
    }
}