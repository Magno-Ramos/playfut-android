package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.model.relations.TeamWithSchema
import com.magnus.playfut.domain.repository.TeamRepository
import com.magnus.playfut.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundPlayingTeamViewModel(
    private val repository: TeamRepository
) : ViewModel() {

    private val _teamState = MutableStateFlow<UiState<TeamWithSchema>>(UiState.Loading)
    val teamState: StateFlow<UiState<TeamWithSchema>> = _teamState.asStateFlow()

    fun fetchTeam(teamId: String, roundId: String) {
        _teamState.value = UiState.Loading
        viewModelScope.launch {
            repository.getTeamByIdWithSchema(teamId, roundId)
                .onFailure { _teamState.value = UiState.Error() }
                .onSuccess { _teamState.value = UiState.Success(it) }
        }
    }
}