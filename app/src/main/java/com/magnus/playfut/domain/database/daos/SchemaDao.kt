package com.magnus.playfut.domain.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoPlayerWithRole
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoSchemaWithPlayers

@Dao
interface SchemaDao {
    @Transaction
    @Query("SELECT * FROM schema_player_cross_ref WHERE schemaId = :schemaId")
    suspend fun getPlayersWithRole(schemaId: Long): List<PojoPlayerWithRole>

    @Transaction
    @Query("SELECT * FROM schemas WHERE teamId = :teamId AND roundId = :roundId LIMIT 1")
    suspend fun getSchemaByTeamAndRound(teamId: String, roundId: String): PojoSchemaWithPlayers?
}