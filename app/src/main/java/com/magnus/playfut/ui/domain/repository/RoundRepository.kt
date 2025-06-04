package com.magnus.playfut.ui.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.database.entities.relations.PojoRoundWithDetails
import com.magnus.playfut.ui.domain.datasource.RoundDataSource
import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.domain.model.Team
import com.magnus.playfut.ui.domain.repository.local.LocalRoundRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteRoundRepository

class RoundRepository(
    private val localDataSource: LocalRoundRepository,
    private val remoteDataSource: RemoteRoundRepository,
    private val auth: FirebaseAuth
) : RoundDataSource {

    private val source
        get() = if (auth.currentUser != null) remoteDataSource else localDataSource

    override suspend fun createRound(groupId: String, teams: List<Team>): Result<Long> {
        return source.createRound(groupId, teams)
    }

    override suspend fun fetchRunningRound(groupId: String): Result<Round?> {
        return source.fetchRunningRound(groupId)
    }

    override suspend fun fetchRoundDetails(roundId: String): Result<PojoRoundWithDetails> {
        return source.fetchRoundDetails(roundId)
    }
}