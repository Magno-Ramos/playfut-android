package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "rounds",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["groupId"],
            childColumns = ["groupOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["groupOwnerId"])
    ]
)
data class RoundEntity(
    @PrimaryKey(autoGenerate = true)
    val roundId: Long = 0,
    val groupOwnerId: Long,
    val date: Date = Date(),
    val opened: Boolean = true
)