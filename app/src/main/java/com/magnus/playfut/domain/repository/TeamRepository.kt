package com.magnus.playfut.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.domain.model.relations.TeamWithSchema
import com.magnus.playfut.domain.model.structure.Team
import com.magnus.playfut.domain.repository.datasource.TeamDataSource
import com.magnus.playfut.domain.repository.local.LocalTeamRepository
import com.magnus.playfut.domain.repository.remote.RemoteTeamRepository

class TeamRepository(
    private val localRepository: LocalTeamRepository,
    private val remoteRepository: RemoteTeamRepository,
    private val auth: FirebaseAuth
) : TeamDataSource {

    private val source: TeamDataSource
        get() = if (auth.currentUser != null) remoteRepository else localRepository

    override suspend fun getTeamById(teamId: String): Result<Team?> {
        return source.getTeamById(teamId)
    }

    override suspend fun getTeamByIdWithSchema(teamId: String, roundId: String): Result<TeamWithSchema> {
        return source.getTeamByIdWithSchema(teamId, roundId)
    }
}