package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

class PojoRoundWithDetails (
    @Embedded val round: RoundEntity,

    @Relation(
        parentColumn = "roundId",     // PK de RoundEntity
        entityColumn = "roundId",     // FK em MatchEntity
        entity = MatchEntity::class   // Especifica a entidade para o Room saber qual tabela consultar
    )
    val matchesWithScoresAndTeams: List<PojoMatchWithScoresAndTeams>, // Lista das partidas com seus scores e times

    // Se você usa RoundTeamCrossRefEntity para saber os times participantes na rodada (não apenas nas partidas)
    @Relation(
        parentColumn = "roundId",
        entityColumn = "teamId",
        associateBy = Junction(RoundTeamCrossRefEntity::class)
    )
    val teamsInRound: List<TeamEntity> // Lista de times que participam da rodada (se aplicável)
)