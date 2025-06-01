package com.magnus.playfut.ui.features.rounds.sorting.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.Team
import com.magnus.playfut.ui.domain.repository.LocalPlayerRepository
import com.magnus.playfut.ui.domain.repository.RemotePlayerRepository
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.features.rounds.sorting.form.model.SelectablePlayer
import com.magnus.playfut.ui.features.rounds.sorting.form.model.toSelectablePlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundSortViewModel(
    private val remoteRepository: RemotePlayerRepository,
    private val localRepository: LocalPlayerRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var playersLoaded: Boolean = false

    private val _playersState = MutableStateFlow<UiState<List<Player>>>(UiState.Loading)
    val playersState = _playersState.asStateFlow()

    private val _selectablePlayers = MutableStateFlow<List<SelectablePlayer>>(emptyList())
    val selectablePlayers = _selectablePlayers.asStateFlow()

    var teams: List<Team>? = null

    fun toggleSelection(player: SelectablePlayer) {
        _selectablePlayers.value = _selectablePlayers.value.map {
            if (it.id == player.id) {
                it.copy(selected = !it.selected)
            } else {
                it
            }
        }
    }

    fun fetchPlayers(groupId: String) {
        if (playersLoaded) {
            return
        }

        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        viewModelScope.launch {
            _playersState.value = UiState.Loading
            repo.fetchPlayers(groupId)
                .onSuccess { players ->
                    _playersState.value = UiState.Success(players)
                    _selectablePlayers.value = players.map { it.toSelectablePlayer() }
                    playersLoaded = true
                }
                .onFailure {
                    _playersState.value = UiState.Error(it.message ?: "Desculpe, ocorreu um erro")
                }
        }
    }
}