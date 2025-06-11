package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.RoundDao
import com.magnus.playfut.ui.domain.helper.DistributorTeamSchema
import com.magnus.playfut.ui.domain.model.relations.RoundWithTeams
import com.magnus.playfut.ui.domain.model.relations.toRoundWithTeams
import com.magnus.playfut.ui.domain.model.structure.Round
import com.magnus.playfut.ui.domain.model.structure.toRound
import com.magnus.playfut.ui.domain.repository.datasource.RoundDataSource

class LocalRoundRepository(
    private val dao: RoundDao,
) : RoundDataSource {

    override suspend fun createRound(groupId: String, schemas: List<DistributorTeamSchema>): Result<Long> {
        return runCatching { dao.insertTeamsWithSchema(groupId.toLong(), schemas) }
    }

    override suspend fun closeRound(roundId: String): Result<Unit> {
        return runCatching { dao.closeRound(roundId.toLong()) }
    }

    override suspend fun closeAllRoundsByGroup(groupId: String): Result<Unit> {
        return runCatching { dao.closeOpenRoundsByGroup(groupId.toLong()) }
    }

    override suspend fun getRoundById(roundId: String): Result<Round> {
        return runCatching { dao.getRoundById(roundId.toLong()).toRound() }
    }

    override suspend fun getRoundWithTeamsById(roundId: String): Result<RoundWithTeams> {
        return runCatching { dao.getRoundWithTeams(roundId).toRoundWithTeams() }
    }
}