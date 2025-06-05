package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import com.magnus.playfut.ui.domain.database.entities.structure.GroupEntity

data class PojoGroupWithPlayersAndRoundsCount(
    @Embedded
    val group: GroupEntity,
    val playerCount: Int,
    val roundCount: Int
)