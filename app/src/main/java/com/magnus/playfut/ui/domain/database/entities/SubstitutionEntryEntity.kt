package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "substitutions",
    foreignKeys = [
        ForeignKey(entity = RoundEntity::class, parentColumns = ["id"], childColumns = ["roundId"]),
        ForeignKey(entity = TeamInRoundEntity::class, parentColumns = ["id"], childColumns = ["teamInRoundId"]),
        ForeignKey(entity = PlayerEntity::class, parentColumns = ["id"], childColumns = ["playerOutId"]),
        ForeignKey(entity = PlayerEntity::class, parentColumns = ["id"], childColumns = ["playerInId"])
    ]
)
data class SubstitutionEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val roundId: Long,
    val teamInRoundId: Long,
    val playerOutId: Long,
    val playerInId: Long
)
