package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.model.structure.Team

interface TeamDataSource {
    suspend fun getTeamById(teamId: String): Result<Team?>
}