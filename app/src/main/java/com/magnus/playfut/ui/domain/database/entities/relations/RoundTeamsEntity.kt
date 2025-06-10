package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity

data class RoundWithTeamsEntity(
    @Embedded val round: RoundEntity,
)