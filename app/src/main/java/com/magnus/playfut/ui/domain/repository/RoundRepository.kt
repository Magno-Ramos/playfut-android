package com.magnus.playfut.ui.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.helper.DistributorTeamSchema
import com.magnus.playfut.ui.domain.model.relations.RoundWithDetails
import com.magnus.playfut.ui.domain.model.structure.Round
import com.magnus.playfut.ui.domain.repository.datasource.RoundDataSource
import com.magnus.playfut.ui.domain.repository.local.LocalRoundRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteRoundRepository

class RoundRepository(
    private val localDataSource: LocalRoundRepository,
    private val remoteDataSource: RemoteRoundRepository,
    private val auth: FirebaseAuth
) : RoundDataSource {

    private val source
        get() = if (auth.currentUser != null) remoteDataSource else localDataSource

    override suspend fun createRound(
        groupId: String,
        schema: List<DistributorTeamSchema>
    ): Result<Long> = source.createRound(groupId, schema)

    override suspend fun closeRound(roundId: String): Result<Unit> {
        return source.closeRound(roundId)
    }

    override suspend fun closeAllRoundsByGroup(groupId: String): Result<Unit> {
        return source.closeAllRoundsByGroup(groupId)
    }

    override suspend fun getRoundById(roundId: String): Result<Round> {
        return source.getRoundById(roundId)
    }

    override suspend fun getRoundWithDetails(roundId: String): Result<RoundWithDetails> {
        return source.getRoundWithDetails(roundId)
    }
}