package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.ui.domain.database.entities.relations.PojoMatchWithScoresAndTeams
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity

@Dao
interface MatchDao {
    @Transaction
    @Query("SELECT * FROM MatchEntity WHERE id = :matchId")
    suspend fun getMatchWithScores(matchId: Long): PojoMatchWithScoresAndTeams?


    @Query("SELECT * FROM MatchEntity WHERE roundId = :roundId ORDER BY id ASC")
    suspend fun getMatchesForRound(roundId: Long): List<MatchEntity>
}