package com.magnus.playfut.domain.helper

import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerPosition

data class DistributorTeamSchema(
    val teamName: String,
    val goalKeepers: List<Player>,
    val startPlaying: List<Player>,
    val substitutes: MutableList<Player> = mutableListOf()
)

object PlayerDistributorV2 {
    fun distribute(
        players: List<Player>,
        teamCount: Int,
        startersPerTeam: Int,
        distributionType: DistributionType
    ): List<DistributorTeamSchema> {
        val finalTeams = mutableListOf<DistributorTeamSchema>()
        require(players.size >= teamCount) { "Not enough players to distribute." }

        val goalkeepers = players.filter { it.position == PlayerPosition.GOALKEEPER }
            .shuffled()
            .toMutableList()

        val fieldPlayers = when (distributionType) {
            DistributionType.RANDOM -> {
                players.filter { it.position != PlayerPosition.GOALKEEPER }
                    .shuffled()
                    .toMutableList()
            }
            DistributionType.BALANCED_BY_RATING -> {
                players.filter { it.position != PlayerPosition.GOALKEEPER }
                    .sortedByDescending { it.skillLevel }
                    .toMutableList()
            }
        }

        repeat(teamCount) { teamIndex ->
            val teamGoalKeepers = mutableListOf<Player>()
            val teamStartPlaying = mutableListOf<Player>()
            val teamSubstitutes = mutableListOf<Player>()

            if (goalkeepers.isNotEmpty()) {
                val gk = goalkeepers.removeAt(0)
                teamGoalKeepers.add(gk)
            }

            repeat(startersPerTeam - (if (teamGoalKeepers.isNotEmpty()) 1 else 0) ) {
                if (fieldPlayers.isNotEmpty()) {
                    val player = fieldPlayers.removeAt(0)
                    teamStartPlaying.add(player)
                }
            }

            finalTeams.add(
                DistributorTeamSchema(
                    teamName = "Time ${teamIndex + 1}",
                    goalKeepers = teamGoalKeepers,
                    startPlaying = teamStartPlaying,
                    substitutes = teamSubstitutes
                )
            )
        }

        var currentTeamIndex = 0
        while (fieldPlayers.isNotEmpty()) {
            val player = fieldPlayers.removeFirstOrNull() ?: break
            finalTeams[currentTeamIndex % teamCount].substitutes.add(player)
            currentTeamIndex++
        }

        return finalTeams
    }
}