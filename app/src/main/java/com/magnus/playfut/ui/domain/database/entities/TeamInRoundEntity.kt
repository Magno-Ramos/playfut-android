package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "teams_in_round",
    foreignKeys = [
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["teamId"]),
        ForeignKey(entity = RoundEntity::class, parentColumns = ["id"], childColumns = ["roundId"])
    ]
)
data class TeamInRoundEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val teamId: Long,
    val roundId: Long
)
