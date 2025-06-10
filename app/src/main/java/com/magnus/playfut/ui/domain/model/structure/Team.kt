package com.magnus.playfut.ui.domain.model.structure

import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

data class Team(
    val id: String,
    val name: String
)

data class Schema(
    var id: String,
    var teamId: String,
    var goalKeepers: List<Player>,
    var startPlaying: List<Player>,
    var substitutes: List<Player>,
    var replacementSuggestions: List<Replacement>
)

class Replacement(
    val id: String,
    val schemaId: String,
    val playerIn: Player,
    val playerOut: Player
)

fun TeamEntity.toTeam() = Team(
    id = teamId.toString(),
    name = name
)