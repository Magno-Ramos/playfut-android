package com.magnus.playfut.ui.features.rounds.playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.repository.MatchRepository
import com.magnus.playfut.ui.domain.repository.PlayerRepository
import com.magnus.playfut.ui.domain.repository.RoundRepository
import com.magnus.playfut.ui.domain.repository.ScoreRepository
import com.magnus.playfut.ui.domain.repository.TeamRepository
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayingHomeViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundPlayingViewModel(
    private val roundRepository: RoundRepository,
    private val scoreRepository: ScoreRepository,
    private val playerRepository: PlayerRepository,
    private val teamRepository: TeamRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _roundState = MutableStateFlow<UiState<RoundPlayingHomeViewState>>(UiState.Loading)
    val roundState = _roundState.asStateFlow()

    private val _closeRoundState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val closeRoundState = _closeRoundState.asStateFlow()

    fun fetchRound(roundId: String) {
        viewModelScope.launch {
            runCatching {
                val round = roundRepository.getRoundById(roundId).getOrThrow()
                val teams = teamRepository.getTeamsByRound(roundId).getOrThrow()
                val matches = matchRepository.getMatchesFromRound(roundId).getOrThrow()
                val scores = scoreRepository.getScoresByRound(roundId).getOrThrow()
                val players = playerRepository.fetchPlayers(round.groupId).getOrThrow()
                _roundState.value = UiState.Success(
                    data = RoundPlayingHomeViewState(
                        groupId = round.groupId,
                        teams = teams,
                        matches = matches,
                        scores = scores,
                        players = players
                    )
                )
            }.onFailure {
                _roundState.value = UiState.Error(null)
            }
        }
    }

    fun closeRound(groupId: String) {
        viewModelScope.launch {
            _closeRoundState.value = UiState.Loading
            roundRepository.closeAllRoundsByGroup(groupId)
                .onFailure { _closeRoundState.value = UiState.Error(it.message) }
                .onSuccess { _closeRoundState.value = UiState.Success(Unit) }
        }
    }
}