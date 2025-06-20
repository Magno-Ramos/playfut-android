package com.magnus.playfut.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.domain.model.relations.RoundResult
import com.magnus.playfut.domain.model.relations.RoundWithDetails
import com.magnus.playfut.domain.model.structure.Round
import com.magnus.playfut.domain.model.ui.TeamSchema
import com.magnus.playfut.domain.repository.datasource.RoundDataSource
import com.magnus.playfut.domain.repository.local.LocalRoundRepository
import com.magnus.playfut.domain.repository.remote.RemoteRoundRepository

class RoundRepository(
    private val localDataSource: LocalRoundRepository,
    private val remoteDataSource: RemoteRoundRepository,
    private val auth: FirebaseAuth
) : RoundDataSource {

    private val source
        get() = if (auth.currentUser != null) remoteDataSource else localDataSource

    override suspend fun createRound(
        groupId: String,
        schema: List<TeamSchema>
    ): Result<Long> = source.createRound(groupId, schema)

    override suspend fun closeRound(roundId: String, winnerTeamId: String?): Result<Unit> {
        return source.closeRound(roundId, winnerTeamId)
    }

    override suspend fun getRoundById(roundId: String): Result<Round> {
        return source.getRoundById(roundId)
    }

    override suspend fun getRoundWithDetails(roundId: String): Result<RoundWithDetails> {
        return source.getRoundWithDetails(roundId)
    }

    override suspend fun getRoundResultList(groupId: String): Result<List<RoundResult>> {
        return source.getRoundResultList(groupId)
    }
}