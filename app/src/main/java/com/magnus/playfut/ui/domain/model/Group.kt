package com.magnus.playfut.ui.domain.model

import com.magnus.playfut.ui.domain.database.entities.relations.PojoGroupWithPlayersAndRoundsCount
import java.util.Date

class Group(
    val id: String = "",
    val name: String = "",
    val playersCount: Int = 0,
    val roundsCount: Int = 0,
    val createdAt: Date = Date()
)

fun PojoGroupWithPlayersAndRoundsCount.toGroup() = Group(
    id = this.group.id.toString(),
    name = this.group.name,
    playersCount = this.playerCount,
    roundsCount = this.roundCount,
    createdAt = this.group.createdAt
)