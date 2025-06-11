package com.magnus.playfut.ui.domain.database.entities.relations.pojo

import androidx.room.Embedded
import com.magnus.playfut.ui.domain.database.entities.structure.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity

class PojoGroupWithOpenedRoundEntity(
    @Embedded val group: GroupEntity,
    @Embedded val round: RoundEntity?,
    val playerCount: Int = 0,
    val roundCount: Int = 0
)