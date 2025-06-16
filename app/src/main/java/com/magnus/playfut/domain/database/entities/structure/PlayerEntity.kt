package com.magnus.playfut.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.magnus.playfut.domain.model.structure.PlayerPosition
import com.magnus.playfut.domain.model.structure.PlayerType

@Entity(
    tableName = "players",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["groupId"],
            childColumns = ["groupOwnerId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index(value = ["groupOwnerId"])
    ]
)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val playerId: Long = 0,
    val name: String = "",
    val skillLevel: Int = 0,
    val groupOwnerId: Long = 0,
    val position: PlayerPosition = PlayerPosition.UNIVERSAL,
    val type: PlayerType = PlayerType.MEMBER,
    val active: Boolean = true
)