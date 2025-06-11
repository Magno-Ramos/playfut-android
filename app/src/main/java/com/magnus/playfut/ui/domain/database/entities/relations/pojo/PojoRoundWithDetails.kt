package com.magnus.playfut.ui.domain.database.entities.relations.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.relations.crossref.CrossRefTeamRound
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

class PojoRoundWithDetails(
    @Embedded val round: RoundEntity,

    @Relation(
        parentColumn = "roundId",
        entityColumn = "teamId",
        associateBy = Junction(
            value = CrossRefTeamRound::class,
            parentColumn = "roundId",
            entityColumn = "teamId"
        )
    )
    val teams: List<TeamEntity>,

    @Relation(
        parentColumn = "roundId",
        entityColumn = "roundId",
        entity = MatchEntity::class
    )
    val matches: List<PojoMatchWithScoresAndTeams>,
)