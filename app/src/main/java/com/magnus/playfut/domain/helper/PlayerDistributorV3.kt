package com.magnus.playfut.domain.helper

import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerPosition
import com.magnus.playfut.domain.model.ui.TeamSchema

object PlayerDistributorV3 {
    fun distribute(
        teamCount: Int,
        startersPerTeam: Int,
        players: List<Player>,
        distributionType: DistributionType
    ): List<TeamSchema> {
        return when (distributionType) {
            DistributionType.RANDOM -> distributeRandom(teamCount, startersPerTeam, players)
            DistributionType.BALANCED_BY_RATING -> distributeByRating(teamCount, startersPerTeam, players)
        }
    }

    private fun distributeByRating(
        teamCount: Int,
        startersPerTeam: Int,
        players: List<Player>
    ): List<TeamSchema> {
        val allGoalKeepers = players.filter { it.position.isGoalkeeper() }
            .shuffled()
            .toMutableList()

        val allFieldPlayers = players.filter { it.position.isFieldPlayer() }
            .shuffled()
            .toMutableList()

        // creating teams
        val teams = mutableListOf<MutableList<Player>>()
        repeat(teamCount) { teams.add(mutableListOf()) }

        allFieldPlayers.groupBy { it.skillLevel }.forEach {
            it.value.shuffled().forEach { player ->
                teams.minByOrNull { it.size }?.add(player)
            }
        }

        // adding goalkeepers to team
        while (allGoalKeepers.isNotEmpty()) {
            val player = allGoalKeepers.removeAt(0)
            teams.minByOrNull { it.size }?.add(player)
        }

        return teams.mapIndexed { index, teamPlayers ->
            val teamGoalKeepers = teamPlayers.filter { it.position.isGoalkeeper() }.shuffled().toMutableList()
            val teamFieldPlayers = teamPlayers.filter { it.position.isFieldPlayer() }.shuffled().toMutableList()
            val startPlaying = mutableListOf<Player>()

            repeat(startersPerTeam - (if (teamGoalKeepers.isNotEmpty()) 1 else 0)) {
                if (teamFieldPlayers.isNotEmpty()) {
                    startPlaying.add(teamFieldPlayers.removeAt(0))
                }
            }

            val substitutions = RotationGenerator.generate(startPlaying, teamFieldPlayers)
            val substitutionsGroups = RotationGenerator.groupSubstitutionsByRounds(substitutions, teamFieldPlayers.size)

            TeamSchema(
                teamName = "Time ${index + 1}",
                goalKeepers = teamGoalKeepers,
                startPlaying = startPlaying,
                substitutes = teamFieldPlayers,
                substitutions = substitutionsGroups
            )
        }
    }

    private fun distributeRandom(
        teamCount: Int,
        startersPerTeam: Int,
        players: List<Player>
    ): List<TeamSchema> {
        val goalkeepers = players.filter { it.position == PlayerPosition.GOALKEEPER }
            .shuffled()
            .toMutableList()

        val fieldPlayers = players.filter { it.position != PlayerPosition.GOALKEEPER }
            .shuffled()
            .toMutableList()

        val teams = mutableListOf<MutableList<Player>>()
        repeat(teamCount) { teams.add(mutableListOf()) }

        // adding goalkeepers to team
        while (goalkeepers.isNotEmpty()) {
            val player = goalkeepers.removeAt(0)
            teams.minByOrNull { it.size }?.add(player)
        }

        while (fieldPlayers.isNotEmpty()) {
            val player = fieldPlayers.removeAt(0)
            teams.minByOrNull { it.size }?.add(player)
        }

        teams.map { it.shuffled() }

        return teams.mapIndexed { index, teamPlayers ->
            val teamGoalKeepers = teamPlayers.filter { it.position.isGoalkeeper() }
            val teamFieldPlayers = teamPlayers.filter { it.position.isFieldPlayer() }.toMutableList()
            val startPlaying = mutableListOf<Player>()

            repeat(startersPerTeam - (if (teamGoalKeepers.isNotEmpty()) 1 else 0)) {
                if (teamFieldPlayers.isNotEmpty()) {
                    startPlaying.add(teamFieldPlayers.removeAt(0))
                }
            }

            val substitutions = RotationGenerator.generate(startPlaying, teamFieldPlayers)
            val substitutionsGroups = RotationGenerator.groupSubstitutionsByRounds(substitutions, teamFieldPlayers.size)

            TeamSchema(
                teamName = "Time ${index + 1}",
                goalKeepers = teamGoalKeepers,
                startPlaying = startPlaying,
                substitutes = teamFieldPlayers,
                substitutions = substitutionsGroups
            )
        }
    }
}