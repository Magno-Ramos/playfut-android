package com.magnus.playfut.ui.domain.model.relations

import com.magnus.playfut.ui.domain.database.entities.relations.pojo.PojoGroupWithOpenedRoundEntity
import com.magnus.playfut.ui.domain.model.structure.Round
import com.magnus.playfut.ui.domain.model.structure.toRound
import java.util.Date

data class GroupWithOpenedRound(
    val id: String = "",
    val name: String = "",
    val playersCount: Int = 0,
    val roundsCount: Int = 0,
    val createdAt: Date = Date(),
    val openedRound: Round? = null
) {
    fun hasOpenedRound() = openedRound != null
}

fun PojoGroupWithOpenedRoundEntity.toGroupWithOpenedRound(): GroupWithOpenedRound {
    return GroupWithOpenedRound(
        id = group.groupId.toString(),
        name = group.name,
        playersCount = playerCount,
        roundsCount = roundCount,
        createdAt = group.createdAt,
        openedRound = round?.toRound()
    )
}