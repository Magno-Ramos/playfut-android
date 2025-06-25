package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group_stats")
class GroupStatsEntity(
    @PrimaryKey val groupId: Long,
    val totalRounds: Int = 0,
    val totalMatches: Int = 0,
    val totalScores: Int = 0
)