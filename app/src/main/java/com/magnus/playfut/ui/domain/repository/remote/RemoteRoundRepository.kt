package com.magnus.playfut.ui.domain.repository.remote

import com.magnus.playfut.ui.domain.helper.DistributorTeamSchema
import com.magnus.playfut.ui.domain.model.relations.RoundWithTeams
import com.magnus.playfut.ui.domain.model.structure.Round
import com.magnus.playfut.ui.domain.repository.datasource.RoundDataSource

class RemoteRoundRepository : RoundDataSource {
    override suspend fun createRound(
        groupId: String,
        schema: List<DistributorTeamSchema>
    ): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun closeRound(roundId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun closeAllRoundsByGroup(groupId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getRoundById(roundId: String): Result<Round> {
        TODO("Not yet implemented")
    }

    override suspend fun getRoundWithTeamsById(roundId: String): Result<RoundWithTeams> {
        TODO("Not yet implemented")
    }
}