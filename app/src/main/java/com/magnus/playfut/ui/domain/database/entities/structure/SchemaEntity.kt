package com.magnus.playfut.ui.domain.database.entities.structure

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
        )
    ],
    indices = [
        Index("teamId")
    ]
)
data class SchemaEntity(
    @PrimaryKey(autoGenerate = true)
    val schemaId: Long = 0,
    val teamId: Long
)
