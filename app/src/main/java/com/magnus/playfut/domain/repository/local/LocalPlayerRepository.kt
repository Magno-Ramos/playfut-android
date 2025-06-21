package com.magnus.playfut.domain.repository.local

import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerPosition
import com.magnus.playfut.domain.model.structure.PlayerType
import com.magnus.playfut.domain.model.structure.toPlayer
import com.magnus.playfut.domain.repository.datasource.PlayerDataSource

class LocalPlayerRepository(
    private val dao: PlayerDao
) : PlayerDataSource {
    override suspend fun createPlayer(
        groupId: String,
        name: String,
        type: PlayerPosition,
        quality: Int,
        playerType: PlayerType
    ) = runCatching {
        dao.insertPlayer(
            PlayerEntity(
                groupOwnerId = groupId.toLong(),
                name = name,
                position = type,
                skillLevel = quality,
                type = playerType
            )
        )
    }

    override suspend fun editPlayer(
        id: String,
        groupId: String,
        name: String,
        type: PlayerPosition,
        quality: Int
    ) = runCatching {
        dao.updatePlayer(
            PlayerEntity(
                playerId = id.toLong(),
                name = name,
                position = type,
                groupOwnerId = groupId.toLong(),
                skillLevel = quality
            )
        )
    }

    override suspend fun removePlayer(id: String): Result<Unit> = runCatching {
        val player = dao.getPlayerById(id.toLong())
        dao.updatePlayer(player.copy(active = false))
    }

    override suspend fun fetchPlayers(groupId: String) = runCatching {
        dao.getPlayersByGroupId(groupId.toLong()).map { it.toPlayer() }
    }

    override suspend fun fetchPlayersByTeam(
        teamId: String,
        roundId: String
    ): Result<List<Player>> {
        return runCatching { dao.getPlayersByTeamInRound(teamId, roundId).map { it.toPlayer() } }
    }

    override suspend fun fetchPlayerScoreRanking(groupId: String): Result<List<Artillery>> {
        return kotlin.runCatching { dao.getPlayersScoreRanking(groupId) }
    }
}