package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.model.relations.RoundResult
import com.magnus.playfut.domain.model.relations.RoundWithDetails
import com.magnus.playfut.domain.model.structure.Round
import com.magnus.playfut.domain.model.ui.TeamSchema

interface RoundDataSource {
    suspend fun createRound(
        groupId: String,
        schema: List<TeamSchema>
    ): Result<Long>

    suspend fun closeRound(roundId: String, winnerTeamId: String?): Result<Unit>

    suspend fun getRoundById(roundId: String): Result<Round>

    suspend fun getRoundWithDetails(roundId: String): Result<RoundWithDetails>

    suspend fun getRoundResultList(groupId: String): Result<List<RoundResult>>
}