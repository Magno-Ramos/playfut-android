package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "round_result_table",
    foreignKeys = [
        ForeignKey(
            entity = RoundEntity::class,
            parentColumns = ["roundId"],
            childColumns = ["roundId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["winnerTeamId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["roundId"]),
        Index(value = ["winnerTeamId"])
    ]
)
data class RoundResultEntity(
    @PrimaryKey(autoGenerate = true)
    val roundResultId: Long = 0,
    val roundId: Long,
    val winnerTeamId: Long?
)