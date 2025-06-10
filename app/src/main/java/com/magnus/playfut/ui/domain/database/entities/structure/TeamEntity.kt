package com.magnus.playfut.ui.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "teams",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = RoundEntity::class,
            parentColumns = ["roundId"],
            childColumns = ["roundId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("roundId")
    ]
)
data class TeamEntity(
    @PrimaryKey(autoGenerate = true)
    val teamId: Long = 0,
    val name: String,
    val roundId: Long
)