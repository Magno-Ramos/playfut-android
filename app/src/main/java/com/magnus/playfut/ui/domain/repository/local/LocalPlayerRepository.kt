package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.PlayerDao
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.structure.toPlayer
import com.magnus.playfut.ui.domain.datasource.PlayerDataSource
import com.magnus.playfut.ui.domain.model.PlayerType

class LocalPlayerRepository (
    private val dao: PlayerDao
) : PlayerDataSource {
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
                playerId = id.toLong(),
                name = name,
                type = type,
                groupId = groupId.toLong(),
                skillLevel = quality
            )
        )
    }

    override suspend fun deletePlayer(id: String): Result<Unit> = runCatching {
        dao.deletePlayer(PlayerEntity(playerId = id.toLong()))
    }

    override suspend fun fetchPlayers(groupId: String) = runCatching {
        dao.getPlayersByGroupId(groupId.toLong()).map { it.toPlayer() }
    }
}