package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "matches",
    foreignKeys = [
        ForeignKey(
            entity = RoundEntity::class,
            parentColumns = ["roundId"],
            childColumns = ["roundId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["groupId"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["roundId"]),
        Index(value = ["groupId"]),
    ]
)
data class MatchEntity (
    @PrimaryKey(autoGenerate = true)
    val matchId: Long = 0,
    val homeTeamId: Long,
    val awayTeamId: Long,
    val homeTeamScore: Int,
    val awayTeamScore: Int,
    val roundId: Long,
    val groupId: Long,
    val createdAt: Date = Date()
)