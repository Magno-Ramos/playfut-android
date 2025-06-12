package com.magnus.playfut.domain.database.entities.relations.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.domain.database.entities.structure.SchemaEntity
import com.magnus.playfut.domain.database.entities.structure.TeamEntity

data class PojoSchemaWithPlayers(
    @Embedded val schema: SchemaEntity,

    @Relation(
        parentColumn = "teamId",
        entityColumn = "teamId"
    )
    val team: TeamEntity
)