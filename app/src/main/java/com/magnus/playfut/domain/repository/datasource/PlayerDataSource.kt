package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.domain.model.structure.Player

interface PlayerDataSource {
    suspend fun createPlayer(player: PlayerEntity): Result<Unit>

    suspend fun editPlayer(player: PlayerEntity): Result<Unit>

    suspend fun removePlayer(id: String): Result<Unit>

    suspend fun fetchPlayers(groupId: String): Result<List<Player>>

    suspend fun fetchPlayersByTeam(teamId: String, roundId: String): Result<List<Player>>

    suspend fun fetchPlayerScoreRanking(groupId: String): Result<List<Artillery>>
}