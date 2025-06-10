package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.ScoreDao
import com.magnus.playfut.ui.domain.model.structure.Artillery
import com.magnus.playfut.ui.domain.model.structure.Score
import com.magnus.playfut.ui.domain.model.structure.toScore
import com.magnus.playfut.ui.domain.repository.datasource.ScoreDataSource

class LocalScoreRepository (
    private val scoreDao: ScoreDao
) : ScoreDataSource {
    override suspend fun getScoresByRound(roundId: String): Result<List<Score>> {
        return runCatching { scoreDao.getScoresByRound(roundId).map { it.toScore() } }
    }

    override suspend fun fetchPlayerGoalsInRound(targetRoundId: String): Result<List<Artillery>> = runCatching {
        scoreDao.fetchPlayerGoalsInRound(targetRoundId.toLong())
    }
}