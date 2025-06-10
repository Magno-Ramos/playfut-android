package com.magnus.playfut.ui.domain.repository.remote

import com.magnus.playfut.ui.domain.model.structure.Team
import com.magnus.playfut.ui.domain.repository.datasource.TeamDataSource

class RemoteTeamRepository : TeamDataSource {
    override suspend fun getTeamById(teamId: String): Result<Team?> {
        TODO("Not yet implemented")
    }

    override suspend fun getTeamsByRound(roundId: String): Result<List<Team>> {
        TODO("Not yet implemented")
    }
}