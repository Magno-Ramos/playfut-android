package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "round_results",
    foreignKeys = [
        ForeignKey(entity = RoundEntity::class, parentColumns = ["id"], childColumns = ["roundId"]),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["winnerTeamId"])
    ]
)
data class RoundResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val roundId: Long,
    val winnerTeamId: Long
)
