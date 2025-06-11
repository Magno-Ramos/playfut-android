package com.magnus.playfut.ui.domain.database.entities.relations.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.relations.crossref.CrossRefSchemaPlayer
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity

class PojoPlayerWithRole(
    @Embedded val crossRef: CrossRefSchemaPlayer,

    @Relation(
        parentColumn = "playerId",
        entityColumn = "playerId"
    )
    val player: PlayerEntity
)