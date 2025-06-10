package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.TeamDao
import com.magnus.playfut.ui.domain.model.structure.Team
import com.magnus.playfut.ui.domain.model.structure.toTeam
import com.magnus.playfut.ui.domain.repository.datasource.TeamDataSource

class LocalTeamRepository (
    private val dao: TeamDao
) : TeamDataSource {
    override suspend fun getTeamById(teamId: String): Result<Team?> {
        return runCatching { dao.getTeamById(teamId)?.toTeam() }
    }

    override suspend fun getTeamsByRound(roundId: String): Result<List<Team>> {
        return runCatching { dao.getTeamsByRoundId(roundId).map { it.toTeam() } }
    }
}