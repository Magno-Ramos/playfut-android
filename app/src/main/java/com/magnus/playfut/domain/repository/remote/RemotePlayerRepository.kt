package com.magnus.playfut.domain.repository.remote

import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerType
import com.magnus.playfut.domain.repository.datasource.PlayerDataSource

class RemotePlayerRepository : PlayerDataSource {
    override suspend fun createPlayer(
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun editPlayer(
        id: String,
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun removePlayer(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPlayers(groupId: String): Result<List<Player>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPlayersByTeam(
        teamId: String,
        roundId: String
    ): Result<List<Player>> {
        TODO("Not yet implemented")
    }
}