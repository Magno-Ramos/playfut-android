package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import com.magnus.playfut.ui.domain.database.entities.structure.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.toGroup
import com.magnus.playfut.ui.domain.database.entities.structure.toRound
import com.magnus.playfut.ui.domain.model.GroupWithOpenedRound

data class GroupWithOpenedRoundEntity(
    @Embedded val group: GroupEntity,
    @Embedded val round: RoundEntity?,
    val playerCount: Int = 0,
    val roundCount: Int = 0
)

fun GroupWithOpenedRoundEntity.toGroupWithOpenedRound(): GroupWithOpenedRound {
    return GroupWithOpenedRound(
        runningRound = round?.toRound(),
        group = group.toGroup(
            playersCount = playerCount,
            roundsCount = roundCount
        )
    )
}