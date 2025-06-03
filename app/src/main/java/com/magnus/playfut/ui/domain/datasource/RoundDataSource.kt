package com.magnus.playfut.ui.domain.datasource

import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.domain.model.Team

interface RoundDataSource {
    suspend fun createRound(groupId: String, teams: List<Team>): Result<Long>

    suspend fun fetchRunningRound(groupId: String): Result<Round?>
}