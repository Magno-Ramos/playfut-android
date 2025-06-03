package com.magnus.playfut.ui.domain.repository.remote

import com.magnus.playfut.ui.domain.datasource.RoundDataSource
import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.domain.model.Team

class RemoteRoundRepository : RoundDataSource {
    override suspend fun createRound(
        groupId: String,
        teams: List<Team>
    ): Result<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchRunningRound(groupId: String): Result<Round?> {
        TODO("Not yet implemented")
    }
}