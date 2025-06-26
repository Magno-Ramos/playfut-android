package com.magnus.playfut.domain.repository

import com.magnus.playfut.domain.database.daos.PlayerDao
import com.magnus.playfut.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.toPlayer
import com.magnus.playfut.domain.repository.datasource.PlayerDataSource

class PlayerRepository(
    private val dao: PlayerDao
) : PlayerDataSource {

    override suspend fun createPlayer(player: PlayerEntity): Result<Unit> = runCatching {
        dao.insertPlayer(player)
    }

    override suspend fun editPlayer(player: PlayerEntity): Result<Unit> = runCatching {
        dao.updatePlayer(player)
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
        return runCatching {
            dao.getPlayersByTeamInRound(teamId, roundId).map {
                it.toPlayer()
            }
        }
    }

    override suspend fun fetchPlayerScoreRanking(groupId: String): Result<List<Artillery>> {
        return kotlin.runCatching { dao.getPlayersScoreRanking(groupId) }
    }
}