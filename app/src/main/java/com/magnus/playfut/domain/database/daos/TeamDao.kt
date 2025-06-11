package com.magnus.playfut.domain.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.magnus.playfut.domain.database.entities.structure.TeamEntity

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    suspend fun getTeamById(teamId: String): TeamEntity?
}