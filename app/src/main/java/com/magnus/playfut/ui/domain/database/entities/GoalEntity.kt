package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "goals",
    foreignKeys = [
        ForeignKey(entity = RoundEntity::class, parentColumns = ["id"], childColumns = ["roundId"]),
        ForeignKey(entity = PlayerEntity::class, parentColumns = ["id"], childColumns = ["scorerId"]),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["id"], childColumns = ["countedForTeamId"])
    ]
)
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val roundId: Long,
    val scorerId: Long,
    val countedForTeamId: Long,
    val isOwnGoal: Boolean = false
)
