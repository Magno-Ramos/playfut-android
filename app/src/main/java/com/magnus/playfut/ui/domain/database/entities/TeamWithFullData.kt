package com.magnus.playfut.ui.domain.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TeamWithFullData(
    @Embedded
    val teamInRound: TeamInRoundEntity,

    @Relation(
        entity = PlayerInTeamInRoundEntity::class,
        parentColumn = "id",
        entityColumn = "teamInRoundId"
    )
    val players: List<PlayerInTeamWithPlayer>,

    @Relation(
        entity = SubstitutionEntryEntity::class,
        parentColumn = "id",
        entityColumn = "teamInRoundId"
    )
    val substitutions: List<SubstitutionWithPlayers>
)
