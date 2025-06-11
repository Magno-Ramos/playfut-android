package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

data class PojoRoundWithTeams(
    @Embedded val round: RoundEntity,

    @Relation(
        parentColumn = "roundId",
        entityColumn = "teamId",
        associateBy = Junction(
            value = TeamRoundCrossRef::class,
            parentColumn = "roundId",
            entityColumn = "teamId"
        )
    )
    val teams: List<TeamEntity>
)