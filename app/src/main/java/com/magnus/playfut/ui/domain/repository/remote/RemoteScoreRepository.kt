package com.magnus.playfut.ui.domain.repository.remote

import com.magnus.playfut.ui.domain.model.structure.Artillery
import com.magnus.playfut.ui.domain.model.structure.Score
import com.magnus.playfut.ui.domain.repository.datasource.ScoreDataSource

class RemoteScoreRepository : ScoreDataSource {
    override suspend fun getScoresByRound(roundId: String): Result<List<Score>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPlayerGoalsInRound(targetRoundId: String): Result<List<Artillery>> {
        TODO("Not yet implemented")
    }
}