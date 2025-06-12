package com.magnus.playfut.domain.model.structure

import com.magnus.playfut.domain.database.entities.structure.TeamEntity

data class Team(
    val id: String,
    val name: String
)

fun TeamEntity.toTeam() = Team(
    id = teamId.toString(),
    name = name
)