package com.magnus.playfut.ui.domain.model

import java.util.Date

class Round(
    val date: Date,
    val teams: List<Team>
)

class Team(
    val id: String,
    val name: String,
    val schema: TeamSchema
)

class TeamSchema(
    val id: String,
    val goalKeepers: List<String>,
    val startPlaying: List<String>,
    val substitutes: List<String>,
    val replacementSuggestions: List<ReplacementSuggestion>
)

class ReplacementSuggestion(
    val playerIn: String,
    val playerOut: String
)

// NEXT STEP

class RoundMatch(
    val id: Long,
    val round: Round,
    val homeTeam: Team,
    val awayTeam: Team
)

class RoundResult(
    val round: Round,
    val winner: Team
)

enum class PlayerPosition {
    GOLEIRO, ZAGUEIRO, MEIA, ATACANTE
}

class GoalEntry(
    val roundId: Long,
    val scorerId: String,
    val countedForTeamId: String,
    val isOwnGoal: Boolean = false
)

class SubstitutionEntry(
    val teamId: String,
    val playerInId: String,
    val playerOutId: String
)
