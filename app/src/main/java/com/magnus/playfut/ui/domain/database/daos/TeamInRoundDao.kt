package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magnus.playfut.ui.domain.database.entities.TeamInRoundEntity

@Dao
interface TeamInRoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamInRound(teamInRound: TeamInRoundEntity): Long

    @Query("SELECT * FROM teams_in_round WHERE roundId = :roundId")
    suspend fun getTeamsInRound(roundId: Long): List<TeamInRoundEntity>
}