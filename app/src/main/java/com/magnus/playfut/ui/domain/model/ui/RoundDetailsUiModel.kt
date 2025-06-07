package com.magnus.playfut.ui.domain.model.ui

import com.magnus.playfut.ui.domain.database.entities.relations.PojoRoundWithDetails

data class RoundDetailsUiModel(
    val roundId: Long,
    val roundDisplayName: String,
    val matches: List<MatchUiModel>,
    val teams: List<TeamUiModel>,
)

fun PojoRoundWithDetails.toUiModel(
    getPlayerName: (Long) -> String? = { id -> "Jogador $id" },
    getTeamName: (Long) -> String? = { id -> "Time $id" }
) = RoundDetailsUiModel(
    roundId = this.round.roundId,
    roundDisplayName = "Rodada ${this.round.roundId}",
    matches = this.matchesWithScoresAndTeams.map { it.toUiModel(getPlayerName, getTeamName) },
    teams = this.teamsInRound.map { team ->
        // calculate total winner of team
        val matches = this.matchesWithScoresAndTeams
        val winnerCountAsAway = matches.count { it.awayTeam?.teamId == team.teamId && it.awayScore < it.awayScore }
        val winnerCountAsHome = matches.count { it.homeTeam?.teamId == team.teamId && it.homeScore > it.awayScore }
        val totalWinners = winnerCountAsHome + winnerCountAsAway
        team.toTeamUiModel(totalWinners)
    }
)