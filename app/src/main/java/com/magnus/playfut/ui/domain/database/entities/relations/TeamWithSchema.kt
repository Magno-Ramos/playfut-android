package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.relations.schema.SchemaWithPlayersAndReplacements
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

data class TeamWithSchema(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "teamId"
    )
    val schema: SchemaWithPlayersAndReplacements
)