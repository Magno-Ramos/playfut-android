package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.RoundDao
import com.magnus.playfut.ui.domain.database.entities.relations.toRound
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity
import com.magnus.playfut.ui.domain.datasource.RoundDataSource
import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.domain.model.Team

class LocalRoundRepository(
    private val dao: RoundDao
) : RoundDataSource {
    override suspend fun createRound(groupId: String, teams: List<Team>): Result<Long> = runCatching {
        val roundEntity = RoundEntity(groupId = groupId.toLong())
        val teamEntities = teams.map { TeamEntity(name = it.name) }
        dao.insertRoundWithTeams(roundEntity, teamEntities)
    }

    override suspend fun fetchRunningRound(groupId: String): Result<Round?> = runCatching {
        dao.getRunningRound(groupId.toLong())?.toRound()
    }
}