package com.magnus.playfut.ui.domain.database.entities.relations.schema

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.relations.SchemaPlayerCrossRef
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity

data class PlayerWithRole(
    @Embedded val crossRef: SchemaPlayerCrossRef,

    @Relation(
        parentColumn = "playerId",
        entityColumn = "playerId"
    )
    val player: PlayerEntity
)
