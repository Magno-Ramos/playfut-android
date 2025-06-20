package com.magnus.playfut.domain.model.ui

import com.magnus.playfut.domain.helper.Substitution
import com.magnus.playfut.domain.model.structure.Player

data class TeamSchema(
    val teamName: String,
    val goalKeepers: List<Player>,
    val startPlaying: List<Player>,
    val substitutes: MutableList<Player> = mutableListOf(),
    val substitutions: List<List<Substitution>> = listOf()
)