package com.magnus.playfut.domain.model.ui

import com.magnus.playfut.domain.database.entities.structure.ScoreEntity

data class ScoreUiModel(
    val scorerName: String?,
    val scoringTeamName: String?,
    val isOwnGoal: Boolean,
    val description: String
)

fun ScoreEntity.toUiModel(
    getPlayerName: (Long) -> String? = { id -> "Jogador $id" },
    getTeamName: (Long) -> String? = { id -> "Time $id" }
): ScoreUiModel {
    val scorerActualName = getPlayerName(this.playerId) ?: "Desconhecido"
    val teamActualName = getTeamName(this.teamIdScored) ?: "Time Desconhecido"
    val ownGoalDisplay = if (this.isOwnGoal) " (GC)" else ""
    return ScoreUiModel(
        scorerName = scorerActualName,
        scoringTeamName = teamActualName,
        isOwnGoal = this.isOwnGoal,
        description = "$scorerActualName$ownGoalDisplay"
    )
}