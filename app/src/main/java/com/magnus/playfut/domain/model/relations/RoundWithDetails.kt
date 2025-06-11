package com.magnus.playfut.domain.model.relations

import com.magnus.playfut.domain.database.entities.relations.pojo.PojoRoundWithDetails
import com.magnus.playfut.domain.model.structure.Match
import com.magnus.playfut.domain.model.structure.Round
import com.magnus.playfut.domain.model.structure.Score
import com.magnus.playfut.domain.model.structure.Team
import com.magnus.playfut.domain.model.structure.toMatch
import com.magnus.playfut.domain.model.structure.toRound
import com.magnus.playfut.domain.model.structure.toScore
import com.magnus.playfut.domain.model.structure.toTeam

data class RoundWithDetails (
    val data: Round,
    val teams: List<Team>,
    val matches: List<Match>,
    val scores: List<Score>
)

fun PojoRoundWithDetails.toRoundWithDetails() = RoundWithDetails(
    data = this.round.toRound(),
    teams = this.teams.map { it.toTeam() },
    matches = this.matches.map { it.match.toMatch() },
    scores = this.matches.map { it.scores }.flatten().map { it.toScore() }
)