package com.magnus.playfut.ui.domain.database.entities.relations.schema

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.relations.SchemaPlayerCrossRef
import com.magnus.playfut.ui.domain.database.entities.structure.SchemaEntity

data class SchemaWithPlayersAndRoles(
    @Embedded val schema: SchemaEntity,
    @Relation(
        parentColumn = "schemaId",
        entityColumn = "playerId",
        associateBy = Junction(SchemaPlayerCrossRef::class)
    )
    val playersWithRoles: List<PlayerWithRole>
)