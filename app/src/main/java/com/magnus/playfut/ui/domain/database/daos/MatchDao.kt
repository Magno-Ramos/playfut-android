package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.ui.domain.database.entities.relations.MatchWithScoresAndTeams
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity

@Dao
interface MatchDao {
    @Transaction
    @Query("SELECT * FROM MatchEntity WHERE id = :matchId")
    suspend fun getMatchWithScores(matchId: Long): MatchWithScoresAndTeams?

    @Query("SELECT * FROM MatchEntity WHERE roundId = :roundId ORDER BY id ASC")
    suspend fun getMatchesForRound(roundId: String): List<MatchEntity>
}