package com.magnus.playfut.ui.domain.database.entities.relations.pojo

import androidx.room.Embedded
import com.magnus.playfut.ui.domain.database.entities.structure.GroupEntity

class PojoGroupWithPlayersAndRoundsCount(
    @Embedded
    val group: GroupEntity,
    val playerCount: Int,
    val roundCount: Int
)