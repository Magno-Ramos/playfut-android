package com.magnus.playfut.ui.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "replacements",
    foreignKeys = [
        ForeignKey(
            entity = SchemaEntity::class,
            parentColumns = ["id"],
            childColumns = ["schemaId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["playerId"],
            childColumns = ["playerInId"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["playerId"],
            childColumns = ["playerOutId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index("schemaId"),
        Index("playerInId"),
        Index("playerOutId")
    ]
)
data class ReplacementEntity(
    @PrimaryKey val id: String,
    val schemaId: String,
    val playerInId: String,
    val playerOutId: String
)