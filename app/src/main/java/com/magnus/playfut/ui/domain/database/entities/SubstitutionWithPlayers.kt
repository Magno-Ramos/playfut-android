package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SubstitutionWithPlayers(
    @Embedded
    val substitution: SubstitutionEntryEntity,

    @Relation(
        parentColumn = "playerOutId",
        entityColumn = "id"
    )
    val playerOut: PlayerEntity,

    @Relation(
        parentColumn = "playerInId",
        entityColumn = "id"
    )
    val playerIn: PlayerEntity
)
