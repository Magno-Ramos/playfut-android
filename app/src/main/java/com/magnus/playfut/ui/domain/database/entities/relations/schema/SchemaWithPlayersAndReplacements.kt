package com.magnus.playfut.ui.domain.database.entities.relations.schema

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.ReplacementEntity
import com.magnus.playfut.ui.domain.database.entities.structure.SchemaEntity

data class SchemaWithPlayersAndReplacements(
    @Embedded val schema: SchemaEntity,

    @Relation(
        entity = SchemaPlayerCrossRef::class,
        parentColumn = "schemaId",
        entityColumn = "schemaId"
    )
    val playersWithRole: List<PlayerWithRole>,

    @Relation(
        parentColumn = "schemaId",
        entityColumn = "schemaId",
        entity = ReplacementEntity::class
    )
    val replacements: List<ReplacementWithPlayers>
)
