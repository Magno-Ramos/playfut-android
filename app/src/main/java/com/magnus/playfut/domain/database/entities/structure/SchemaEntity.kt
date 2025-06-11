package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "schemas",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoundEntity::class,
            parentColumns = ["roundId"],
            childColumns = ["roundId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("teamId"),
        Index("roundId")
    ]
)
data class SchemaEntity(
    @PrimaryKey(autoGenerate = true)
    val schemaId: Long = 0,
    val teamId: Long,
    val roundId: Long
)
