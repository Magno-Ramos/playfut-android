package com.magnus.playfut.domain.repository.remote

import com.magnus.playfut.domain.model.structure.Team
import com.magnus.playfut.domain.repository.datasource.TeamDataSource

class RemoteTeamRepository : TeamDataSource {
    override suspend fun getTeamById(teamId: String): Result<Team?> {
        TODO("Not yet implemented")
    }
}