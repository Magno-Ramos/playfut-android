package com.magnus.playfut.domain.model.form

data class MatchForm(
    val roundId: String,
    val homeTeamId: String,
    val awayTeamId: String,
    val scores: List<MatchItemScore>
)

data class MatchItemScore(
    val playerId: String,
    val scoredTeamId: String,
    val isOwnGoal: Boolean = false
)

