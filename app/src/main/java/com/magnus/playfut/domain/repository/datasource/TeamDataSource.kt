package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.model.relations.TeamWithSchema
import com.magnus.playfut.domain.model.structure.Team

interface TeamDataSource {
    suspend fun getTeamById(teamId: String): Result<Team?>

    suspend fun getTeamByIdWithSchema(teamId: String, roundId: String): Result<TeamWithSchema>
}