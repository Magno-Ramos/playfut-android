package com.magnus.playfut.ui.domain.model.ui

import com.magnus.playfut.ui.domain.database.entities.relations.PojoMatchWithScoresAndTeams

data class MatchUiModel(
    val matchId: Long,
    val homeTeamName: String,
    val awayTeamName: String,
    val homeTeamScore: Int,
    val awayTeamScore: Int,
    val scores: List<ScoreUiModel>
)

fun PojoMatchWithScoresAndTeams.toUiModel(
    getPlayerName: (Long) -> String? = { id -> "Jogador $id" },
    getTeamName: (Long) -> String? = { id -> "Time $id" }
): MatchUiModel {
    val calculatedHomeScore = this.scores.count { it.teamIdScored == this.match.homeTeamId }
    val calculatedAwayScore = this.scores.count { it.teamIdScored == this.match.awayTeamId }

    return MatchUiModel(
        matchId = this.match.id,
        homeTeamName = this.homeTeam?.name ?: "Desconhecido",
        awayTeamName = this.awayTeam?.name ?: "Desconhecido",
        homeTeamScore = calculatedHomeScore,
        awayTeamScore = calculatedAwayScore,
        scores = this.scores.map { it.toUiModel(getPlayerName, getTeamName) }
    )
}