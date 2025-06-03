package com.magnus.playfut.ui.domain.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity
import com.magnus.playfut.ui.domain.database.entities.structure.toTeam
import com.magnus.playfut.ui.domain.model.Round

data class RoundWithTeamsEntity(
    @Embedded val round: RoundEntity,
    @Relation(
        parentColumn = "roundId",       // Primary key of the current entity (RoundEntity)
        entity = TeamEntity::class,     // The target related entity
        entityColumn = "teamId",        // Primary key of the target related entity (TeamEntity)
        associateBy = Junction(
            value = RoundTeamCrossRefEntity::class,
            parentColumn = "roundId",   // Column in RoundTeamCrossRefEntity that references RoundEntity's PK
            entityColumn = "teamId"     // Column in RoundTeamCrossRefEntity that references TeamEntity's PK
        )
    )
    val teams: List<TeamEntity>
)

fun RoundWithTeamsEntity.toRound() = Round(
    date = round.date,
    opened = round.opened,
    teams = teams.map { it.toTeam() }
)