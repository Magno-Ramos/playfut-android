package com.magnus.playfut.ui.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    val groupId: Long = 0,
    val name: String = "",
    val createdAt: Date = Date()
)