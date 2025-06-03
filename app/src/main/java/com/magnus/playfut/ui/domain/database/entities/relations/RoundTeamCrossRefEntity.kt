package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["roundId", "teamId"])
data class RoundTeamCrossRefEntity(
    val roundId: Long,
    @ColumnInfo(index = true) val teamId: Long
)
