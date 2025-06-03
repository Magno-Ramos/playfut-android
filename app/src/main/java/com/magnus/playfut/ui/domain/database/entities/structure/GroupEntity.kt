package com.magnus.playfut.ui.domain.database.entities.structure

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.Round
import java.util.Date

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val createdAt: Date = Date()
)

fun GroupEntity.toGroup() = Group(
    id = id.toString(),
    name = name
)

fun PlayerEntity.toPlayer() = Player(
    id = playerId.toString(),
    groupId = groupId.toString(),
    name = name,
    skillLevel = skillLevel,
    type = type
)

fun RoundEntity.toRound() = Round(
    date = date,
    opened = opened,
    teams = listOf()
)