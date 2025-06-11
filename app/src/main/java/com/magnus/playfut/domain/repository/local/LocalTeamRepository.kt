package com.magnus.playfut.domain.repository.local

import com.magnus.playfut.domain.database.daos.TeamDao
import com.magnus.playfut.domain.model.structure.Team
import com.magnus.playfut.domain.model.structure.toTeam
import com.magnus.playfut.domain.repository.datasource.TeamDataSource

class LocalTeamRepository (
    private val dao: TeamDao
) : TeamDataSource {
    override suspend fun getTeamById(teamId: String): Result<Team?> {
        return runCatching { dao.getTeamById(teamId)?.toTeam() }
    }
}