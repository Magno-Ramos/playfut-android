package com.magnus.playfut.domain.database.entities.relations.crossref

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.magnus.playfut.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.domain.database.entities.structure.TeamEntity

@Entity(
    tableName = "team_round_cross_ref",
    primaryKeys = ["teamId", "roundId"],
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoundEntity::class,
            parentColumns = ["roundId"],
            childColumns = ["roundId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["teamId"]),
        Index(value = ["roundId"])
    ]
)
data class CrossRefTeamRound(
    val teamId: Long,
    val roundId: Long
)