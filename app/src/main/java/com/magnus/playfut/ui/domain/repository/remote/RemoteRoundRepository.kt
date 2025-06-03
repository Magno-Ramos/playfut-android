package com.magnus.playfut.ui.domain.repository.remote

import com.magnus.playfut.ui.domain.datasource.RoundDataSource
import com.magnus.playfut.ui.domain.model.Round

class RemoteRoundRepository : RoundDataSource {
    override suspend fun fetchRunningRound(groupId: String): Result<Round?> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllRounds(groupId: String): Result<List<Round>> {
        TODO("Not yet implemented")
    }
}