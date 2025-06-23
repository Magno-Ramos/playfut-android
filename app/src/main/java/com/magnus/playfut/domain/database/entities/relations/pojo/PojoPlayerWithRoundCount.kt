package com.magnus.playfut.domain.database.entities.relations.pojo

import androidx.room.Embedded
import com.magnus.playfut.domain.database.entities.structure.PlayerEntity

class PojoPlayerWithRoundCount(
    @Embedded
    val player: PlayerEntity,
    val roundCount: Int
)