package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.ui.domain.database.entities.TeamWithPlayersAndSubstitutions

@Dao
interface TeamInRoundWithDetailsDao {

    @Transaction
    @Query("SELECT * FROM teams_in_round WHERE roundId = :roundId AND teamId = :teamId")
    suspend fun getTeamWithPlayersAndSubstitutions(
        roundId: Long,
        teamId: Long
    ): TeamWithPlayersAndSubstitutions?
}