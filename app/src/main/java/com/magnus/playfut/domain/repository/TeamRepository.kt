package com.magnus.playfut.domain.repository

import com.magnus.playfut.domain.database.daos.SchemaDao
import com.magnus.playfut.domain.database.daos.TeamDao
import com.magnus.playfut.domain.model.relations.TeamWithSchema
import com.magnus.playfut.domain.model.relations.toTeamWithSchema
import com.magnus.playfut.domain.model.structure.Team
import com.magnus.playfut.domain.model.structure.toTeam
import com.magnus.playfut.domain.repository.datasource.TeamDataSource

class TeamRepository(
    private val dao: TeamDao,
    private val schemaDao: SchemaDao
) : TeamDataSource {
    override suspend fun getTeamById(teamId: String): Result<Team?> {
        return runCatching { dao.getTeamById(teamId)?.toTeam() }
    }

    override suspend fun getTeamByIdWithSchema(teamId: String, roundId: String): Result<TeamWithSchema> {
        return runCatching {
            val schemaWithTeam = schemaDao.getSchemaByTeamAndRound(teamId, roundId)!!
            val players = schemaDao.getPlayersWithRole(schemaWithTeam.schema.schemaId)
            schemaWithTeam.toTeamWithSchema(players)
        }
    }
}