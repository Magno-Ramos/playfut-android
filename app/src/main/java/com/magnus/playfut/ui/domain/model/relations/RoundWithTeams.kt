package com.magnus.playfut.ui.domain.model.relations

import com.magnus.playfut.ui.domain.database.entities.relations.PojoRoundWithTeams
import com.magnus.playfut.ui.domain.model.structure.Round
import com.magnus.playfut.ui.domain.model.structure.Team
import com.magnus.playfut.ui.domain.model.structure.toRound
import com.magnus.playfut.ui.domain.model.structure.toTeam

data class RoundWithTeams (
    val data: Round,
    val teams: List<Team>
)

fun PojoRoundWithTeams.toRoundWithTeams() = RoundWithTeams(
    data = this.round.toRound(),
    teams = this.teams.map { it.toTeam() }
)