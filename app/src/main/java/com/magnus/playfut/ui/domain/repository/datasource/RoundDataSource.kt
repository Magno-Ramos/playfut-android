package com.magnus.playfut.ui.domain.repository.datasource

import com.magnus.playfut.ui.domain.helper.DistributorTeamSchema
import com.magnus.playfut.ui.domain.model.structure.Round

interface RoundDataSource {
    suspend fun createRound(
        groupId: String,
        schema: List<DistributorTeamSchema>
    ): Result<Long>

    suspend fun closeRound(roundId: String): Result<Unit>

    suspend fun closeAllRoundsByGroup(groupId: String): Result<Unit>

    suspend fun getRoundById(roundId: String): Result<Round>
}