package com.magnus.playfut.domain.repository.remote

import com.magnus.playfut.domain.model.relations.RoundResult
import com.magnus.playfut.domain.model.relations.RoundWithDetails
import com.magnus.playfut.domain.model.structure.Round
import com.magnus.playfut.domain.model.ui.TeamSchema
import com.magnus.playfut.domain.repository.datasource.RoundDataSource

class RemoteRoundRepository : RoundDataSource {
    override suspend fun createRound(
        groupId: String,
        schema: List<TeamSchema>
    ): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun closeRound(roundId: String, winnerTeamId: String?): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getRoundById(roundId: String): Result<Round> {
        TODO("Not yet implemented")
    }

    override suspend fun getRoundWithDetails(roundId: String): Result<RoundWithDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun getRoundResultList(groupId: String): Result<List<RoundResult>> {
        TODO("Not yet implemented")
    }
}