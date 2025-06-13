package com.magnus.playfut.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerType
import com.magnus.playfut.domain.repository.datasource.PlayerDataSource
import com.magnus.playfut.domain.repository.local.LocalPlayerRepository
import com.magnus.playfut.domain.repository.remote.RemotePlayerRepository

class PlayerRepository(
    private val localRepository: LocalPlayerRepository,
    private val remoteRepository: RemotePlayerRepository,
    private val auth: FirebaseAuth
) : PlayerDataSource {

    private val source
        get() = if (auth.currentUser != null) remoteRepository else localRepository

    override suspend fun createPlayer(
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ): Result<Unit> = source.createPlayer(groupId, name, type, quality)

    override suspend fun editPlayer(
        id: String,
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ): Result<Unit> = source.editPlayer(id, groupId, name, type, quality)

    override suspend fun removePlayer(id: String): Result<Unit> {
        return source.removePlayer(id)
    }

    override suspend fun fetchPlayers(groupId: String): Result<List<Player>> {
        return source.fetchPlayers(groupId)
    }

    override suspend fun fetchPlayersByTeam(
        teamId: String,
        roundId: String
    ): Result<List<Player>> {
        return source.fetchPlayersByTeam(teamId, roundId)
    }
}