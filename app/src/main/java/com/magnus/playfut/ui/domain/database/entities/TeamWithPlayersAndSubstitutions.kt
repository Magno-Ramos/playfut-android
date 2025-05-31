package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TeamWithPlayersAndSubstitutions(
    @Embedded
    val teamInRound: TeamInRoundEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "teamInRoundId",
        entity = PlayerInTeamInRoundEntity::class
    )
    val players: List<PlayerInTeamInRoundEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "teamInRoundId",
        entity = SubstitutionEntryEntity::class
    )
    val substitutions: List<SubstitutionEntryEntity>
)