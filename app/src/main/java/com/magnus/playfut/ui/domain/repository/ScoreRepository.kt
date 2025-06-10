package com.magnus.playfut.ui.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.structure.Artillery
import com.magnus.playfut.ui.domain.model.structure.Score
import com.magnus.playfut.ui.domain.repository.datasource.ScoreDataSource
import com.magnus.playfut.ui.domain.repository.local.LocalScoreRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteScoreRepository

class ScoreRepository (
    private val localScoreRepository: LocalScoreRepository,
    private val remoteScoreRepository: RemoteScoreRepository,
    private val auth: FirebaseAuth
) : ScoreDataSource {

    private val source
        get() = if (auth.currentUser != null) remoteScoreRepository else localScoreRepository

    override suspend fun getScoresByRound(roundId: String): Result<List<Score>> {
        return source.getScoresByRound(roundId)
    }

    override suspend fun fetchPlayerGoalsInRound(targetRoundId: String): Result<List<Artillery>> {
        return source.fetchPlayerGoalsInRound(targetRoundId)
    }
}