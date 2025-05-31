package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "players_in_team_in_round",
    foreignKeys = [
        ForeignKey(entity = TeamInRoundEntity::class, parentColumns = ["id"], childColumns = ["teamInRoundId"]),
        ForeignKey(entity = PlayerEntity::class, parentColumns = ["id"], childColumns = ["playerId"])
    ]
)
data class PlayerInTeamInRoundEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val teamInRoundId: Long,
    val playerId: Long,
    val isStarter: Boolean = false,
    val isGoalkeeper: Boolean = false
)
