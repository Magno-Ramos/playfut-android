package com.magnus.playfut.domain.model.form

import com.magnus.playfut.domain.model.structure.Player

data class MatchForm(
    val groupId: String,
    val roundId: String,
    val homeTeamId: String,
    val awayTeamId: String,
    val homePlayers: List<Player>,
    val awayPlayers: List<Player>,
    val scores: List<MatchItemScore>,
)

data class MatchItemScore(
    val playerId: String,
    val scoredTeamId: String,
    val isOwnGoal: Boolean = false
)

