package com.magnus.playfut.ui.features.rounds.playing.states

import com.magnus.playfut.domain.model.structure.Match
import com.magnus.playfut.domain.model.structure.Score
import com.magnus.playfut.domain.model.structure.Team

class RoundPlayingHomeViewState(
    val groupId: String,
    val players: List<RoundPlayerItem>,
    val roundId: String,
    val roundName: String,
    teams: List<Team>,
    matches: List<Match>,
    scores: List<Score>,
) {
    val teams: List<RoundTeamItem> = teams.map {
        RoundTeamItem(
            id = it.id,
            name = it.name,
            victories = getTeamVictories(it.id, matches, scores)
        )
    }
    val matches: List<RoundMatchItem> = matches.map {
        RoundMatchItem(
            homeTeam = getTeamName(it.homeTeamId, teams),
            awayTeam = getTeamName(it.awayTeamId, teams),
            homeScore = getTeamScore(it.id, it.homeTeamId, scores),
            awayScore = getTeamScore(it.id, it.awayTeamId, scores)
        )
    }
    val artillery: List<RoundArtilleryItem> = scores.filter {
        // filtering only goals pro team
        it.isOwnGoal.not()
    }.groupBy { it.playerId }.map {
        RoundArtilleryItem(
            name = players.find { player -> player.playerId == it.key }?.playerName,
            goals = it.value.size
        )
    }
}

data class RoundTeamItem(
    val id: String,
    val name: String,
    val victories: Int
)

data class RoundMatchItem(
    val homeTeam: String?,
    val awayTeam: String?,
    val homeScore: Int,
    val awayScore: Int
)

data class RoundArtilleryItem(
    val name: String?,
    val goals: Int
)

data class RoundScoreItem(
    val playerId: String,
    val teamId: String,
    val playerName: String,
    val teamName: String
)

data class RoundPlayerItem (
    val teamId: String,
    val playerId: String,
    val playerName: String
)

private fun getTeamName(teamId: String, teams: List<Team>): String? {
    return teams.find { it.id == teamId }?.name
}

private fun getTeamScore(matchId: String, teamId: String, scores: List<Score>): Int {
    return scores.count { it.matchId == matchId && it.teamIdScored == teamId }
}

private fun getTeamVictories(teamId: String, matches: List<Match>, scores: List<Score>): Int {
    return matches.count { match ->
        val homeScore = getTeamScore(match.id, match.homeTeamId, scores)
        val awayScore = getTeamScore(match.id, match.awayTeamId, scores)
        val homeWin = homeScore > awayScore
        val awayWin = awayScore > homeScore
        (match.homeTeamId == teamId && homeWin) || (match.awayTeamId == teamId && awayWin)
    }
}