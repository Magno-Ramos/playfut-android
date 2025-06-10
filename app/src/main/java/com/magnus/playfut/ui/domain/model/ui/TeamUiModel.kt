package com.magnus.playfut.ui.domain.model.ui

import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

data class TeamUiModel(
    val id: String,
    val teamDisplayName: String,
    val teamDisplayDescription: String,
    val victories: Int
)

fun TeamEntity.toTeamUiModel(victories: Int) = TeamUiModel(
    id = teamId.toString(),
    victories = victories,
    teamDisplayName = this.name,
    teamDisplayDescription = when {
        victories == 1 -> "1 Vitória"
        else -> "$victories Vitórias"
    }
)