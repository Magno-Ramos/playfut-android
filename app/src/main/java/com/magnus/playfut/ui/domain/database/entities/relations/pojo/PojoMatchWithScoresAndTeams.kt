package com.magnus.playfut.ui.domain.database.entities.relations.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.ui.domain.database.entities.structure.ScoreEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

class PojoMatchWithScoresAndTeams(
    @Embedded val match: MatchEntity,

    @Relation(
        parentColumn = "homeTeamId",
        entityColumn = "teamId"
    )
    val homeTeam: TeamEntity?,

    @Relation(
        parentColumn = "awayTeamId",
        entityColumn = "teamId"
    )
    val awayTeam: TeamEntity?,

    @Relation(
        parentColumn = "matchId",
        entityColumn = "matchId",
        entity = ScoreEntity::class
    )
    val scores: List<ScoreEntity>
)