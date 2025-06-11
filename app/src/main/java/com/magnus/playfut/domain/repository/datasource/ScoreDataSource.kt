package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.domain.model.structure.Score

interface ScoreDataSource {
    suspend fun getScoresByRound(roundId: String): Result<List<Score>>

    suspend fun fetchPlayerGoalsInRound(targetRoundId: String): Result<List<Artillery>>
}