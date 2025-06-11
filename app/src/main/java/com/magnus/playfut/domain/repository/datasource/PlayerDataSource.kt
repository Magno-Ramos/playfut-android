package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerType

interface PlayerDataSource {
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

    suspend fun fetchPlayersByTeam(teamId: String, roundId: String): Result<List<Player>>
}