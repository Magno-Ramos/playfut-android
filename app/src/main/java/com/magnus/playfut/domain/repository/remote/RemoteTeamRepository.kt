package com.magnus.playfut.domain.repository.remote

import com.magnus.playfut.domain.model.relations.TeamWithSchema
import com.magnus.playfut.domain.model.structure.Team
import com.magnus.playfut.domain.repository.datasource.TeamDataSource

class RemoteTeamRepository : TeamDataSource {
    override suspend fun getTeamById(teamId: String): Result<Team?> {
        TODO("Not yet implemented")
    }

    override suspend fun getTeamByIdWithSchema(teamId: String, roundId: String): Result<TeamWithSchema> {
        TODO("Not yet implemented")
    }
}