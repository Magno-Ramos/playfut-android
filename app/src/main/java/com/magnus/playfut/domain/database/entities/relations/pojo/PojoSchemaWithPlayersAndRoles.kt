package com.magnus.playfut.domain.database.entities.relations.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.magnus.playfut.domain.database.entities.relations.crossref.CrossRefSchemaPlayer
import com.magnus.playfut.domain.database.entities.structure.SchemaEntity

class PojoSchemaWithPlayersAndRoles(
    @Embedded val schema: SchemaEntity,
    @Relation(
        parentColumn = "schemaId",
        entityColumn = "playerId",
        associateBy = Junction(CrossRefSchemaPlayer::class)
    )
    val playersWithRoles: List<PojoPlayerWithRole>
)