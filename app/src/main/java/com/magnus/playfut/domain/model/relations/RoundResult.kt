package com.magnus.playfut.domain.model.relations

import com.magnus.playfut.domain.database.entities.relations.pojo.PojoRoundResultWithRoundAndTeam
import com.magnus.playfut.domain.model.structure.Team
import com.magnus.playfut.domain.model.structure.toTeam
import java.util.Date

class RoundResult (
    val id: String,
    val groupId: String,
    val date: Date,
    val opened: Boolean,
    val winnerTeam: Team?
)

fun PojoRoundResultWithRoundAndTeam.toRoundResult() = RoundResult(
    id = this.round.roundId.toString(),
    groupId = this.round.groupOwnerId.toString(),
    date = this.round.date,
    opened = this.round.opened,
    winnerTeam = this.winnerTeam?.toTeam()
)