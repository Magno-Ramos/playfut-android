package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magnus.playfut.ui.domain.database.entities.PlayerInTeamInRoundEntity

@Dao
interface PlayerInTeamInRoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerInTeam(player: PlayerInTeamInRoundEntity): Long

    @Query("SELECT * FROM players_in_team_in_round WHERE teamInRoundId = :teamInRoundId")
    suspend fun getPlayersInTeam(teamInRoundId: Long): List<PlayerInTeamInRoundEntity>

    @Query("SELECT * FROM players_in_team_in_round WHERE teamInRoundId = :teamInRoundId AND isStarter = 1")
    suspend fun getStarters(teamInRoundId: Long): List<PlayerInTeamInRoundEntity>

    @Query("SELECT * FROM players_in_team_in_round WHERE teamInRoundId = :teamInRoundId AND isGoalkeeper = 1")
    suspend fun getGoalkeepers(teamInRoundId: Long): List<PlayerInTeamInRoundEntity>
}