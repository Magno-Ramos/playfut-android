package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.ui.domain.database.entities.structure.ScoreEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

data class MatchWithScoresAndTeams(
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
        parentColumn = "id",
        entityColumn = "matchId",
        entity = ScoreEntity::class
    )
    val scores: List<ScoreEntity>
) {
    val homeScore: Int
        get() = scores.count { it.teamIdScored == match.homeTeamId }

    val awayScore: Int
        get() = scores.count { it.teamIdScored == match.awayTeamId }
}