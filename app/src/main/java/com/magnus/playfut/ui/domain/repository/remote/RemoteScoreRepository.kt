package com.magnus.playfut.ui.domain.repository.remote

import com.magnus.playfut.ui.domain.datasource.ScoreDataSource
import com.magnus.playfut.ui.domain.model.ui.Artillery

class RemoteScoreRepository : ScoreDataSource {
    override suspend fun fetchPlayerGoalsInRound(targetRoundId: String): Result<List<Artillery>> {
        TODO("Not yet implemented")
    }
}