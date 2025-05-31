package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magnus.playfut.ui.domain.database.entities.GoalEntity

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity): Long

    @Query("SELECT * FROM goals WHERE roundId = :roundId")
    suspend fun getGoalsByRound(roundId: Long): List<GoalEntity>

    @Query("SELECT * FROM goals WHERE scorerId = :playerId")
    suspend fun getGoalsByPlayer(playerId: Long): List<GoalEntity>
}