package com.magnus.playfut.domain.helper

import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerType

data class DistributorTeamSchema(
    val teamName: String,
    val goalKeepers: List<Player>,
    val startPlaying: List<Player>,
    val substitutes: List<Player>
)

object PlayerDistributorV2 {
    fun distribute(
        players: List<Player>,
        teamCount: Int,
        startersPerTeam: Int
    ): List<DistributorTeamSchema> {
        val finalTeams = mutableListOf<DistributorTeamSchema>()
        val goalKeepersCount = teamCount
        require(players.size >= (teamCount * startersPerTeam + goalKeepersCount)) { "Not enough players to distribute." }

        val goalkeepers =
            players.filter { it.type == PlayerType.GOALKEEPER }.sortedByDescending { it.skillLevel }
                .shuffled()
                .toMutableList()

        val fieldPlayers =
            players.filter { it.type != PlayerType.GOALKEEPER }.sortedByDescending { it.skillLevel }
                .shuffled()
                .toMutableList()

        repeat(teamCount) { teamIndex ->
            val teamGoalKeepers = mutableListOf<Player>()
            val teamFieldPlayers = mutableListOf<Player>()
            val teamStartPlaying = mutableListOf<Player>()
            val teamSubstitutes = mutableListOf<Player>()

            // assign goalkeepers
            if (goalkeepers.isNotEmpty()) {
                val gk = goalkeepers.removeAt(0)
                teamGoalKeepers.add(gk)
            }

            // collect players from all to distribute
            repeat(startersPerTeam) {
                if (fieldPlayers.isNotEmpty()) {
                    val player = fieldPlayers.removeAt(0)
                    teamFieldPlayers.add(player)
                }
            }

            // assign starters
            repeat(startersPerTeam) {
                if (teamFieldPlayers.isNotEmpty()) {
                    val player = teamFieldPlayers.removeAt(0)
                    teamStartPlaying.add(player)
                }
            }

            // assign substitutes
            teamFieldPlayers.forEach { player ->
                teamSubstitutes.add(player)
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

        return finalTeams
    }
}