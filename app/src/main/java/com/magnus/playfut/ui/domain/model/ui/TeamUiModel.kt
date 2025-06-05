package com.magnus.playfut.ui.domain.model.ui

import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

data class TeamUiModel(
    val teamDisplayName: String,
    val teamDisplayDescription: String,
    val victories: Int
)

fun TeamEntity.toTeamUiModel(victories: Int) = TeamUiModel(
    victories = victories,
    teamDisplayName = this.name,
    teamDisplayDescription = when {
        victories == 1 -> "1 Vitória"
        else -> "$victories Vitórias"
    }
)