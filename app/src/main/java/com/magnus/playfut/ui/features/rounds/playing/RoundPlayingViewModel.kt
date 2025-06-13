package com.magnus.playfut.ui.features.rounds.playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.model.form.MatchForm
import com.magnus.playfut.domain.repository.GroupRepository
import com.magnus.playfut.domain.repository.MatchRepository
import com.magnus.playfut.domain.repository.PlayerRepository
import com.magnus.playfut.domain.repository.RoundRepository
import com.magnus.playfut.domain.state.ActionResultState
import com.magnus.playfut.domain.state.UiState
import com.magnus.playfut.domain.state.asSuccess
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayerItem
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayingHomeViewState
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayingResultViewState
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundPlayingViewModel(
    private val groupRepository: GroupRepository,
    private val roundRepository: RoundRepository,
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

    var winnerViewState: RoundPlayingResultViewState? = null

    fun fetchRound(roundId: String) {
        viewModelScope.launch {
            runCatching {
                val round = roundRepository.getRoundWithDetails(roundId).getOrThrow()
                val group = groupRepository.getGroupById(round.data.groupId).getOrThrow()
                val players = round.teams.map { team ->
                    playerRepository.fetchPlayersByTeam(team.id, round.data.id).getOrThrow().map { player ->
                        RoundPlayerItem(team.id, player.id, player.name)
                    }
                }.flatten()

                _roundState.value = UiState.Success(
                    data = RoundPlayingHomeViewState(
                        groupId = round.data.groupId,
                        groupName = group.name,
                        teams = round.teams,
                        matches = round.matches,
                        scores = round.scores,
                        players = players,
                        roundName = "Rodada ${round.data.id}",
                        roundId = round.data.id,
                        roundDate = round.data.date
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

    fun closeRound() {
        viewModelScope.launch {
            _closeRoundState.value = ActionResultState.Loading

            val roundState = roundState.value.asSuccess()?.data

            if (roundState == null) {
                _closeRoundState.value = ActionResultState.Error("Unable to close round")
                return@launch
            }

            var winnerTeamId: String? = null
            val maxVictories = roundState.teams.maxOf { it.victories }
            val winners = roundState.teams.filter { it.victories == maxVictories }

            if (winners.size == 1) {
                val winnerTeam = winners.first()
                winnerTeamId = winnerTeam.id

                val homeMatches = roundState.matches.filter { it.homeTeamId == winnerTeamId }
                val awayMatches = roundState.matches.filter { it.awayTeamId == winnerTeamId }

                val goalsScored = homeMatches.sumOf { it.homeScore } + awayMatches.sumOf { it.awayScore }
                val goalsConceded = homeMatches.sumOf { it.awayScore } + awayMatches.sumOf { it.homeScore }

                val winnerTeamDraws = homeMatches.count { it.homeScore == it.awayScore } +
                        awayMatches.count { it.homeScore == it.awayScore }

                val winnerTeamLosses = homeMatches.count { it.homeScore < it.awayScore } +
                        awayMatches.count { it.homeScore > it.awayScore }

                winnerViewState = RoundPlayingResultViewState.Victory(
                    groupName = roundState.groupName,
                    date = roundState.roundDate,
                    teamName = winnerTeam.name,
                    wins = winnerTeam.victories,
                    draws = winnerTeamDraws,
                    losses = winnerTeamLosses,
                    goalsScored = goalsScored,
                    goalsConceded = goalsConceded
                )
            } else {
                winnerViewState = RoundPlayingResultViewState.Draw(
                    groupName = roundState.groupName,
                    date = roundState.roundDate,
                    teams = winners.map { it.name }
                )
            }

            roundRepository.closeRound(roundState.roundId, winnerTeamId)
                .onFailure { _closeRoundState.value = ActionResultState.Error(it.message) }
                .onSuccess { _closeRoundState.value = ActionResultState.Success(Unit) }
        }
    }

    fun resetCloseMatchState() {
        _closeMatchState.value = ActionResultState.Idle
    }
}