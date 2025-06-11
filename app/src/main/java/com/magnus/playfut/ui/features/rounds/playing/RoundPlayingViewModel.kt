package com.magnus.playfut.ui.features.rounds.playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.model.form.MatchForm
import com.magnus.playfut.ui.domain.repository.MatchRepository
import com.magnus.playfut.ui.domain.repository.PlayerRepository
import com.magnus.playfut.ui.domain.repository.RoundRepository
import com.magnus.playfut.ui.domain.repository.ScoreRepository
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayerItem
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayingHomeViewState
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundPlayingViewModel(
    private val roundRepository: RoundRepository,
    private val scoreRepository: ScoreRepository,
    private val playerRepository: PlayerRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _roundState = MutableStateFlow<UiState<RoundPlayingHomeViewState>>(UiState.Loading)
    val roundState = _roundState.asStateFlow()

    private val _closeRoundState = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val closeRoundState = _closeRoundState.asStateFlow()

    private val _closeMatchState = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val closeMatchState = _closeMatchState.asStateFlow()

    var matchHomeTeam: RoundTeamItem? = null
    var matchAwayTeam: RoundTeamItem? = null

    fun fetchRound(roundId: String) {
        viewModelScope.launch {
            runCatching {
                val round = roundRepository.getRoundWithTeamsById(roundId).getOrThrow()
                val matches = matchRepository.getMatchesFromRound(roundId).getOrThrow()
                val scores = scoreRepository.getScoresByRound(roundId).getOrThrow()
                val players = round.teams.map { team ->
                    playerRepository.fetchPlayersByTeam(team.id, round.data.id).getOrThrow().map { player ->
                        RoundPlayerItem(team.id, player.id, player.name)
                    }
                }.flatten()

                _roundState.value = UiState.Success(
                    data = RoundPlayingHomeViewState(
                        groupId = round.data.groupId,
                        teams = round.teams,
                        matches = matches,
                        scores = scores,
                        players = players,
                        roundName = "Rodada ${round.data.id}",
                        roundId = round.data.id
                    )
                )
            }.onFailure {
                _roundState.value = UiState.Error(null)
            }
        }
    }

    fun closeMatch(form: MatchForm) {
        viewModelScope.launch {
            matchRepository.pushMatch(form)
                .onFailure { _closeMatchState.value = ActionResultState.Error(it.message) }
                .onSuccess { _closeMatchState.value = ActionResultState.Success(Unit) }
        }
    }

    fun closeRound(groupId: String) {
        viewModelScope.launch {
            _closeRoundState.value = ActionResultState.Loading
            roundRepository.closeAllRoundsByGroup(groupId)
                .onFailure { _closeRoundState.value = ActionResultState.Error(it.message) }
                .onSuccess { _closeRoundState.value = ActionResultState.Success(Unit) }
        }
    }

    fun resetCloseMatchState() {
        _closeMatchState.value = ActionResultState.Idle
    }
}