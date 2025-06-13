package com.magnus.playfut.domain.repository.local

import com.magnus.playfut.domain.database.daos.RoundDao
import com.magnus.playfut.domain.helper.DistributorTeamSchema
import com.magnus.playfut.domain.model.relations.RoundResult
import com.magnus.playfut.domain.model.relations.RoundWithDetails
import com.magnus.playfut.domain.model.relations.toRoundResult
import com.magnus.playfut.domain.model.relations.toRoundWithDetails
import com.magnus.playfut.domain.model.structure.Round
import com.magnus.playfut.domain.model.structure.toRound
import com.magnus.playfut.domain.repository.datasource.RoundDataSource

class LocalRoundRepository(
    private val dao: RoundDao,
) : RoundDataSource {

    override suspend fun createRound(groupId: String, schema: List<DistributorTeamSchema>): Result<Long> {
        return runCatching { dao.insertTeamsWithSchema(groupId.toLong(), schema) }
    }

    override suspend fun closeRound(roundId: String, winnerTeamId: String?): Result<Unit> {
        return runCatching { dao.closeRoundAndAddResult(roundId, winnerTeamId) }
    }

    override suspend fun getRoundById(roundId: String): Result<Round> {
        return runCatching { dao.getRoundById(roundId.toLong()).toRound() }
    }

    override suspend fun getRoundWithDetails(roundId: String): Result<RoundWithDetails> {
        return runCatching { dao.getRoundDetails(roundId).toRoundWithDetails() }
    }

    override suspend fun getRoundResultList(groupId: String): Result<List<RoundResult>> {
        return runCatching { dao.getRoundsByGroup(groupId.toLong()).map { it.toRoundResult() } }
    }
}