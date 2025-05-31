package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magnus.playfut.ui.domain.database.entities.RoundEntity

@Dao
interface RoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRound(round: RoundEntity): Long

    @Query("SELECT * FROM rounds WHERE groupId = :groupId ORDER BY date DESC")
    suspend fun getRoundsByGroup(groupId: Long): List<RoundEntity>
}