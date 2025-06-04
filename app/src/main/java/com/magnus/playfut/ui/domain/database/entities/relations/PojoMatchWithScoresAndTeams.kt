package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.ui.domain.database.entities.structure.ScoreEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

data class PojoMatchWithScoresAndTeams(
    @Embedded val match: MatchEntity,

    @Relation(
        parentColumn = "homeTeamId",
        entityColumn = "teamId" // Supondo que TeamEntity tem 'teamId' como PK
    )
    val homeTeam: TeamEntity?, // Se você buscar os detalhes do time

    @Relation(
        parentColumn = "awayTeamId",
        entityColumn = "teamId"
    )
    val awayTeam: TeamEntity?, // Se você buscar os detalhes do time

    @Relation(
        parentColumn = "id", // Chave primária de MatchEntity
        entityColumn = "matchId", // Chave estrangeira em ScoreEntity
        entity = ScoreEntity::class
    )
    val scores: List<ScoreEntity>
) {
    val homeScore: Int
        get() = scores.count { it.teamIdScored == match.homeTeamId }

    val awayScore: Int
        get() = scores.count { it.teamIdScored == match.awayTeamId }
}