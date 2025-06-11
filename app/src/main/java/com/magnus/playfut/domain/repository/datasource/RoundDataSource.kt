package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.helper.DistributorTeamSchema
import com.magnus.playfut.domain.model.relations.RoundWithDetails
import com.magnus.playfut.domain.model.structure.Round

interface RoundDataSource {
    suspend fun createRound(
        groupId: String,
        schema: List<DistributorTeamSchema>
    ): Result<Long>

    suspend fun closeRound(roundId: String): Result<Unit>

    suspend fun closeAllRoundsByGroup(groupId: String): Result<Unit>

    suspend fun getRoundById(roundId: String): Result<Round>

    suspend fun getRoundWithDetails(roundId: String): Result<RoundWithDetails>
}