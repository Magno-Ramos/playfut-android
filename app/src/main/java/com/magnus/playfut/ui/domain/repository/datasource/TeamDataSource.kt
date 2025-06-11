package com.magnus.playfut.ui.domain.repository.datasource

import com.magnus.playfut.ui.domain.model.structure.Team

interface TeamDataSource {
    suspend fun getTeamById(teamId: String): Result<Team?>
}