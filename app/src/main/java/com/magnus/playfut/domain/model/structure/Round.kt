package com.magnus.playfut.domain.model.structure

import com.magnus.playfut.domain.database.entities.structure.RoundEntity
import java.util.Date

class Round(
    val id: String,
    val groupId: String,
    val date: Date,
    val opened: Boolean
)

fun RoundEntity.toRound() = Round(
    id = roundId.toString(),
    groupId = groupOwnerId.toString(),
    date = date,
    opened = opened,
)
