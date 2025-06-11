package com.magnus.playfut.ui.domain.database.entities.relations.crossref

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.structure.SchemaEntity

enum class SchemaPlayerRole {
    GOALKEEPER,
    START_PLAYING,
    SUBSTITUTE
}

@Entity(
    tableName = "schema_player_cross_ref",
    primaryKeys = ["schemaId", "playerId"],
    foreignKeys = [
        ForeignKey(
            entity = SchemaEntity::class,
            parentColumns = ["schemaId"],
            childColumns = ["schemaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["playerId"],
            childColumns = ["playerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["playerId"]),
        Index(value = ["schemaId"]),
    ]
)
data class CrossRefSchemaPlayer(
    val schemaId: Long,
    val playerId: Long,
    val role: SchemaPlayerRole
)
