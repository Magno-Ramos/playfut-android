package com.magnus.playfut.ui.domain.datasource

import com.magnus.playfut.ui.domain.model.ui.Artillery

interface ScoreDataSource {
    suspend fun fetchPlayerGoalsInRound(targetRoundId: String): Result<List<Artillery>>
}