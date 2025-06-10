package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    suspend fun getTeamById(teamId: String): TeamEntity?

    @Query("SELECT * FROM teams WHERE roundId = :roundId")
    suspend fun getTeamsByRoundId(roundId: String): List<TeamEntity>
}