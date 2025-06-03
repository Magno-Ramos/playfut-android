package com.magnus.playfut.ui.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.datasource.RoundDataSource
import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.domain.repository.local.LocalRoundRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteRoundRepository

class RoundRepository (
    private val localDataSource: LocalRoundRepository,
    private val remoteDataSource: RemoteRoundRepository,
    private val auth: FirebaseAuth
) : RoundDataSource {

    private val source
        get() = if (auth.currentUser != null) remoteDataSource else localDataSource

    override suspend fun fetchRunningRound(groupId: String): Result<Round?> {
        return source.fetchRunningRound(groupId)
    }

    override suspend fun fetchAllRounds(groupId: String): Result<List<Round>> {
        return source.fetchAllRounds(groupId)
    }
}