package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.magnus.playfut.ui.domain.database.entities.structure.ScoreEntity
import com.magnus.playfut.ui.domain.model.structure.Artillery

@Dao
interface ScoreDao {
    @Query("""
        SELECT
            P.name AS playerName,
            COUNT(S.scoreId) AS totalGoals
        FROM
            ScoreEntity S
        INNER JOIN
            players P ON S.playerId = P.playerId
        WHERE
            S.roundId = :targetRoundId
            AND S.isOwnGoal = 0
        GROUP BY
            P.playerId, P.name
        ORDER BY
            totalGoals DESC
    """)
    suspend fun fetchPlayerGoalsInRound(targetRoundId: Long): List<Artillery>

    @Query("SELECT * FROM ScoreEntity WHERE roundId = :roundId")
    suspend fun getScoresByRound(roundId: String): List<ScoreEntity>
}