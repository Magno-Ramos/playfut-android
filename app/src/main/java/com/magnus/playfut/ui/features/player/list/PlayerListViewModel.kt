package com.magnus.playfut.ui.features.player.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.model.structure.Player
import com.magnus.playfut.ui.domain.repository.PlayerRepository
import com.magnus.playfut.ui.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerListViewModel(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Player>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Player>>> = _uiState

    fun fetchPlayers(groupId: String) {
        viewModelScope.launch {
            repository.fetchPlayers(groupId)
                .onFailure { _uiState.value = UiState.Error(it.message) }
                .onSuccess { _uiState.value = UiState.Success(it) }
        }
    }
}