package com.magnus.playfut.ui.domain.helper

import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.domain.model.ReplacementSuggestion
import com.magnus.playfut.ui.domain.model.Team
import com.magnus.playfut.ui.domain.model.TeamSchema

object PlayerDistributor {

    fun distributeTeamsWithSubstitutions(
        players: List<Player>,
        teamCount: Int,
        startersPerTeam: Int
    ): List<Team> {
        require(players.size >= teamCount * startersPerTeam) {
            "Jogadores insuficientes para os titulares dos times"
        }

        val goalkeepers =
            players.filter { it.type == PlayerType.GOALKEEPER }.sortedByDescending { it.skillLevel }
                .shuffled()
                .toMutableList()

        val fieldPlayers =
            players.filter { it.type != PlayerType.GOALKEEPER }.sortedByDescending { it.skillLevel }
                .shuffled()
                .toMutableList()

        val teamsSchema = createEmptyTeams(teamCount)

        assignGoalkeepers(teamsSchema, goalkeepers, fieldPlayers)

        assignFieldStarters(teamsSchema, fieldPlayers, startersPerTeam, players)

        assignSubstitutes(teamsSchema, fieldPlayers)

        generateReplacementSuggestions(teamsSchema)

        return teamsSchema.mapIndexed { index, teamSchema ->
            Team(
                id = index.toString(),
                name = "Time ${index + 1}",
                schema = teamSchema
            )
        }
    }

    // 1. Criar lista de times vazios
    private fun createEmptyTeams(teamCount: Int): MutableList<TeamSchema> {
        return MutableList(teamCount) { index ->
            TeamSchema(
                goalKeepers = mutableListOf(),
                startPlaying = mutableListOf(),
                substitutes = mutableListOf(),
                replacementSuggestions = mutableListOf()
            )
        }
    }

    // 2. Distribuir goleiros ou improvisar com rodízio
    private fun assignGoalkeepers(
        teams: MutableList<TeamSchema>,
        goalkeepers: MutableList<Player>,
        fieldPlayers: MutableList<Player>
    ) {
        if (goalkeepers.size >= teams.size) {
            // Goleiros suficientes: 1 por time
            teams.forEach { team ->
                val gk = goalkeepers.removeAt(0)
                (team.goalKeepers as MutableList).add(gk)
            }
        } else {
            // Goleiros insuficientes: usar rodízio
            teams.forEachIndexed { index, team ->
                val gk = goalkeepers.getOrNull(index)
                if (gk != null) {
                    goalkeepers.removeAt(0)
                    (team.goalKeepers as MutableList).add(gk)
                    (team.startPlaying as MutableList).add(gk)
                } else {
                    val improvisedGK = fieldPlayers.removeFirstOrNull()
                    if (improvisedGK != null) {
                        (team.goalKeepers as MutableList).add(improvisedGK)
                        (team.startPlaying as MutableList).add(improvisedGK)
                        // Substituição obrigatória futura
                        val substitute = fieldPlayers.removeFirstOrNull()
                        if (substitute != null) {
                            (team.substitutes as MutableList).add(substitute)
                            (team.replacementSuggestions as MutableList).add(
                                ReplacementSuggestion(substitute, improvisedGK)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun assignFieldStarters(
        teams: MutableList<TeamSchema>,
        fieldPlayers: MutableList<Player>,
        remainingSpots: Int,
        allPlayers: List<Player>
    ) {
        // Primeiro, adiciona o goleiro fixo para cada time (se houver só 1 goleiro)
        teams.forEach { team ->
            val teamGoalKeepers = team.goalKeepers.toSet()
            val fixedGoalKeeper = team.startPlaying.find { it in teamGoalKeepers && teamGoalKeepers.size == 1 }

            if (fixedGoalKeeper == null) {
                // tenta achar o goleiro fixo entre os jogadores (ex: no fieldPlayers)
                val gk = fieldPlayers.find { it in teamGoalKeepers }
                if (gk != null && teamGoalKeepers.size == 1) {
                    (team.startPlaying as MutableList).add(gk)
                    fieldPlayers.remove(gk)
                }
            }
        }
2
        // Depois distribui os outros titulares, excluindo goleiros (eles já foram alocados ou não entram)
        repeat(remainingSpots) {
            teams.sortedBy { team ->
                team.startPlaying.sumOf { p ->
                    allPlayers.find { it.id == p.id }?.skillLevel ?: 0
                }
            }.forEach { team ->
                if (fieldPlayers.isNotEmpty()) {
                    // Pega o primeiro jogador que NÃO é goleiro
                    val playerIndex = fieldPlayers.indexOfFirst { it.type != PlayerType.GOALKEEPER }
                    if (playerIndex != -1) {
                        val player = fieldPlayers.removeAt(playerIndex)
                        (team.startPlaying as MutableList).add(player)
                    }
                }
            }
        }
    }

    // 4. Distribuir reservas uniformemente
    private fun assignSubstitutes(
        teams: MutableList<TeamSchema>,
        fieldPlayers: MutableList<Player>
    ) {
        while (fieldPlayers.isNotEmpty()) {
            teams.sortedBy { it.substitutes.size }.forEach { team ->
                if (fieldPlayers.isNotEmpty()) {
                    val player = fieldPlayers.removeAt(0)
                    (team.substitutes as MutableList).add(player)
                }
            }
        }
    }

    // 5. Criar sugestões de substituição para todos os reservas
    private fun generateReplacementSuggestions(
        teams: MutableList<TeamSchema>
    ) {
        teams.forEach { team ->
            val starters = team.startPlaying.toMutableList()
            val substitutes = team.substitutes.toMutableList()
            val goalkeepers = team.goalKeepers.toSet()

            val allPlayers = (starters + substitutes).distinct()
            val suggestions = mutableListOf<ReplacementSuggestion>()

            val nonReplaceableGKs = starters.filter { it in goalkeepers && goalkeepers.size == 1 }.toSet()
            val playersToReplace = allPlayers.filterNot { it in nonReplaceableGKs }.toMutableSet()

            val alreadyEntered = mutableSetOf<Player>()
            val alreadyExited = mutableSetOf<Player>()

            // Preferencialmente: reservas entram no lugar dos titulares
            val starterPool = starters.filterNot { it in nonReplaceableGKs }.toMutableList()
            val reservePool = substitutes.toMutableList()

            while (starterPool.isNotEmpty() && reservePool.isNotEmpty()) {
                val outId = starterPool.removeAt(0)
                val inId = reservePool.removeAt(0)

                suggestions.add(ReplacementSuggestion(playerIn = inId, playerOut = outId))
                alreadyExited.add(outId)
                alreadyEntered.add(inId)
            }

            // Garantir que TODOS os jogadores tenham saído e entrado uma vez

            val remainingToEnter = playersToReplace.filterNot { it in alreadyEntered }.toMutableList()
            val remainingToExit = playersToReplace.filterNot { it in alreadyExited }.toMutableList()

            // Rodízio interno entre jogadores restantes
            val maxLoop = maxOf(remainingToEnter.size, remainingToExit.size)
            for (i in 0 until maxLoop) {
                val inId = remainingToEnter.getOrNull(i % remainingToEnter.size)
                val outId = remainingToExit.getOrNull(i % remainingToExit.size)

                if (inId != null && outId != null && inId != outId) {
                    suggestions.add(ReplacementSuggestion(playerIn = inId, playerOut = outId))
                    alreadyEntered.add(inId)
                    alreadyExited.add(outId)
                }
            }

            // Finaliza
            (team.replacementSuggestions as MutableList).clear()
            team.replacementSuggestions.addAll(suggestions)
        }
    }
}