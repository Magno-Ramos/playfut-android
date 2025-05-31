package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PlayerInTeamWithPlayer(
    @Embedded
    val playerInTeam: PlayerInTeamInRoundEntity,

    @Relation(
        parentColumn = "playerId",
        entityColumn = "id"
    )
    val player: PlayerEntity
)