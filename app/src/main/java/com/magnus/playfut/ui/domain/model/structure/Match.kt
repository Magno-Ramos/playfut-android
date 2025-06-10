package com.magnus.playfut.ui.domain.model.structure

import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity

data class Match (
    val id: String,
    val homeTeamId: String,
    val awayTeamId: String,
)

fun MatchEntity.toMatch() = Match(
    id = id.toString(),
    homeTeamId = homeTeamId.toString(),
    awayTeamId = awayTeamId.toString()
)