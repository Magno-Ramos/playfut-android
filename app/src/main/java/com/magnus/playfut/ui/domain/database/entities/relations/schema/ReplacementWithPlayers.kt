package com.magnus.playfut.ui.domain.database.entities.relations.schema

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.structure.ReplacementEntity

data class ReplacementWithPlayers(
    @Embedded val replacement: ReplacementEntity,

    @Relation(
        parentColumn = "playerInId",
        entityColumn = "playerId"
    )
    val playerIn: PlayerEntity,

    @Relation(
        parentColumn = "playerOutId",
        entityColumn = "playerId"
    )
    val playerOut: PlayerEntity
)
