package com.magnus.playfut.ui.domain.repository

import com.magnus.playfut.ui.domain.database.daos.PlayerDao
import com.magnus.playfut.ui.domain.database.entities.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.toPlayer
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.PlayerType

interface PlayerRepository {
    suspend fun createPlayer(
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ): Result<Unit>

    suspend fun editPlayer(
        id: String,
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ): Result<Unit>

    suspend fun deletePlayer(id: String): Result<Unit>

    suspend fun fetchPlayers(groupId: String): Result<List<Player>>
}

class RemotePlayerRepository : PlayerRepository {
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

class LocalPlayerRepository(
    private val dao: PlayerDao
) : PlayerRepository {
    override suspend fun createPlayer(
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ) = runCatching {
        dao.insertPlayer(
            PlayerEntity(
                groupId = groupId.toLong(),
                name = name,
                type = type,
                skillLevel = quality
            )
        )
    }

    override suspend fun editPlayer(
        id: String,
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ) = runCatching {
        dao.updatePlayer(
            PlayerEntity(
                id = id.toLong(),
                name = name,
                type = type,
                groupId = groupId.toLong(),
                skillLevel = quality
            )
        )
    }

    override suspend fun deletePlayer(id: String): Result<Unit> = runCatching {
        dao.deletePlayer(PlayerEntity(id = id.toLong()))
    }

    override suspend fun fetchPlayers(groupId: String) = runCatching {
        dao.getPlayersByGroupId(groupId.toLong()).map { it.toPlayer() }
    }
}