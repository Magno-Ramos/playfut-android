package com.magnus.playfut.ui.domain.database.entities.relations.schema

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.structure.SchemaEntity

data class SchemaWithPlayers(
    @Embedded val schema: SchemaEntity,

    @Relation(
        entity = PlayerEntity::class,
        parentColumn = "schemaId",
        entityColumn = "playerId",
        associateBy = Junction(SchemaPlayerCrossRef::class)
    )
    val players: List<PlayerEntity>
)