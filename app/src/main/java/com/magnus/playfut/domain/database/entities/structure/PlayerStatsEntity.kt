package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_stats")
class PlayerStatsEntity (
    @PrimaryKey val playerId: Long,
    val groupId: Long,
    val rounds: Int = 0,
    val matches: Int = 0,
    val wins: Int = 0,
    val goals: Int = 0
)