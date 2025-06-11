package com.magnus.playfut.ui.domain.model.relations

import com.magnus.playfut.ui.domain.database.entities.relations.pojo.PojoGroupWithPlayersAndRoundsCount
import java.util.Date

class GroupWithPlayersAndRoundsCount (
    val id: String = "",
    val name: String = "",
    val playersCount: Int = 0,
    val roundsCount: Int = 0,
    val createdAt: Date = Date()
)

fun PojoGroupWithPlayersAndRoundsCount.toGroup() = GroupWithPlayersAndRoundsCount(
    id = group.groupId.toString(),
    name = group.name,
    playersCount = playerCount,
    roundsCount = roundCount,
    createdAt = group.createdAt
)