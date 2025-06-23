package com.magnus.playfut.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.magnus.playfut.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.domain.model.structure.Artillery

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayer(player: PlayerEntity)

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Delete
    suspend fun deletePlayer(player: PlayerEntity)

    @Query("SELECT * FROM players WHERE playerId = :id")
    suspend fun getPlayerById(id: Long): PlayerEntity

    @Query("SELECT * FROM players WHERE groupOwnerId = :groupId AND active = 1")
    suspend fun getPlayersByGroupId(groupId: Long): List<PlayerEntity>

    @Query(
        """
        SELECT p.*, COUNT(DISTINCT s.roundId) as roundCount FROM players p
        INNER JOIN schema_player_cross_ref c ON p.playerId = c.playerId
        INNER JOIN schemas s ON c.schemaId = s.schemaId
        GROUP BY p.playerId
        ORDER BY roundCount DESC
        LIMIT 1
    """
    )
    suspend fun getMostPresentPlayer(): PlayerEntity

    @Query("""
        SELECT p.*, COUNT(*) as winCount FROM players p
        INNER JOIN schema_player_cross_ref c ON p.playerId = c.playerId
        INNER JOIN schemas s ON c.schemaId = s.schemaId
        INNER JOIN round_result_table rr ON s.roundId = rr.roundId AND s.teamId = rr.winnerTeamId
        GROUP BY p.playerId
        ORDER BY winCount DESC
        LIMIT 1
    """)
    suspend fun getPlayerWithMostWins(): PlayerEntity

    @Transaction
    @Query(
        """
        SELECT P.*
        FROM Players P
        INNER JOIN schema_player_cross_ref SP_CrossRef ON P.playerId = SP_CrossRef.playerId
        INNER JOIN Schemas S ON SP_CrossRef.schemaId = S.schemaId
        INNER JOIN Teams T ON S.teamId = T.teamId
        WHERE T.teamId = :targetTeamId AND S.teamId = :targetTeamId AND S.roundId = :targetRoundId AND P.active = 1
    """
    )
    suspend fun getPlayersByTeamInRound(targetTeamId: String, targetRoundId: String): List<PlayerEntity>

    @Query(
        """
        SELECT
            p.name AS playerName, 
            COUNT(s.scoreId) AS totalGoals
        FROM
            players p
        LEFT JOIN
            scores s ON p.playerId = s.playerId
        WHERE
            p.groupOwnerId = :groupId
        GROUP BY
            p.name
        ORDER BY
            totalGoals DESC;
    """
    )
    suspend fun getPlayersScoreRanking(groupId: String): List<Artillery>
}