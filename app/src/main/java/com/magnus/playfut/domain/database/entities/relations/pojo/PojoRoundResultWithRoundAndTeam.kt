package com.magnus.playfut.domain.database.entities.relations.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.domain.database.entities.structure.RoundResultEntity
import com.magnus.playfut.domain.database.entities.structure.TeamEntity

data class PojoRoundResultWithRoundAndTeam(
    @Embedded val roundResult: RoundResultEntity,

    @Relation(
        parentColumn = "roundId",
        entityColumn = "roundId",
        entity = RoundEntity::class
    )
    val round: RoundEntity,

    @Relation(
        parentColumn = "winnerTeamId",
        entityColumn = "teamId",
        entity = TeamEntity::class
    )
    val winnerTeam: TeamEntity?
)