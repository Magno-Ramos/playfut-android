package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity

class RoundWithDetails(
    @Embedded val round: RoundEntity,

    @Relation(
        parentColumn = "roundId",
        entityColumn = "roundId",
        entity = MatchEntity::class
    )
    val matches: List<MatchWithScoresAndTeams>,
)