package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.ScoreDao
import com.magnus.playfut.ui.domain.datasource.ScoreDataSource
import com.magnus.playfut.ui.domain.model.ui.Artillery

class LocalScoreRepository (
    private val scoreDao: ScoreDao
) : ScoreDataSource {
    override suspend fun fetchPlayerGoalsInRound(targetRoundId: String): Result<List<Artillery>> = runCatching {
        scoreDao.fetchPlayerGoalsInRound(targetRoundId.toLong())
    }
}