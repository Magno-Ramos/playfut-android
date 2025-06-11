package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey(autoGenerate = true)
    val teamId: Long = 0,
    val name: String
)