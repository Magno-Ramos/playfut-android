package com.magnus.playfut.ui.domain.repository.remote

import com.magnus.playfut.ui.domain.datasource.PlayerDataSource
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.PlayerType

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

    override suspend fun deletePlayer(id: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPlayers(groupId: String): Result<List<Player>> {
        TODO("Not yet implemented")
    }
}