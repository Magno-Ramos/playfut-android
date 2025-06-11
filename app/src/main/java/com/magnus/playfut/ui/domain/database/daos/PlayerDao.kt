package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayer(player: PlayerEntity)

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Delete
    suspend fun deletePlayer(player: PlayerEntity)

    @Query("SELECT * FROM players WHERE groupId = :groupId")
    suspend fun getPlayersByGroupId(groupId: Long): List<PlayerEntity>

    @Transaction
    @Query("""
        SELECT P.*
        FROM Players P
        INNER JOIN schema_player_cross_ref SP_CrossRef ON P.playerId = SP_CrossRef.playerId
        INNER JOIN Schemas S ON SP_CrossRef.schemaId = S.schemaId
        INNER JOIN Teams T ON S.teamId = T.teamId
        WHERE T.teamId = :targetTeamId AND S.teamId = :targetTeamId AND S.roundId = :targetRoundId
    """)
    suspend fun getPlayersByTeamInRound(targetTeamId: String, targetRoundId: String): List<PlayerEntity>
}