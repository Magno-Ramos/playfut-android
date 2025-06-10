package com.magnus.playfut.ui.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.magnus.playfut.ui.domain.model.structure.PlayerType

@Entity(
    tableName = "players",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index(value = ["groupId"])
    ]
)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val playerId: Long = 0,
    val name: String = "",
    val skillLevel: Int = 0,
    val groupId: Long = 0,
    val type: PlayerType = PlayerType.UNIVERSAL
)