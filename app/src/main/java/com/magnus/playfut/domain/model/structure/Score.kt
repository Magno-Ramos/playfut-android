package com.magnus.playfut.domain.model.structure

import com.magnus.playfut.domain.database.entities.structure.ScoreEntity

data class Score (
    val scoreId: String,
    val playerId: String,
    val roundId: String,
    val matchId: String,
    val teamIdScored: String,
    val isOwnGoal: Boolean = false
)

fun ScoreEntity.toScore() = Score(
    scoreId = scoreId.toString(),
    playerId = playerId.toString(),
    roundId = roundId.toString(),
    matchId = matchId.toString(),
    teamIdScored = teamIdScored.toString(),
)