package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.ui.domain.database.entities.TeamWithFullData

@Dao
interface TeamInRoundWithFullDataDao {

    @Transaction
    @Query("SELECT * FROM teams_in_round WHERE roundId = :roundId AND teamId = :teamId")
    suspend fun getTeamWithFullData(
        roundId: Long,
        teamId: Long
    ): TeamWithFullData?
}
