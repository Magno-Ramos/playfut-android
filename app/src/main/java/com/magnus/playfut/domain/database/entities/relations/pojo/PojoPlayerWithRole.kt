package com.magnus.playfut.domain.database.entities.relations.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.domain.database.entities.relations.crossref.CrossRefSchemaPlayer
import com.magnus.playfut.domain.database.entities.structure.PlayerEntity

data class PojoPlayerWithRole(
    @Embedded
    val crossRef: CrossRefSchemaPlayer,

    @Relation(
        parentColumn = "playerId",
        entityColumn = "playerId"
    )
    val player: PlayerEntity
)