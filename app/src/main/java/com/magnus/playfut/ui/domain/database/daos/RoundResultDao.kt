package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magnus.playfut.ui.domain.database.entities.RoundResultEntity

@Dao
interface RoundResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoundResult(result: RoundResultEntity): Long

    @Query("SELECT * FROM round_results WHERE roundId = :roundId")
    suspend fun getResultForRound(roundId: Long): RoundResultEntity?
}