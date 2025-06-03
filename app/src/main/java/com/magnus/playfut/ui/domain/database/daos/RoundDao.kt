package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.ui.domain.database.entities.relations.RoundTeamCrossRefEntity
import com.magnus.playfut.ui.domain.database.entities.relations.RoundWithTeamsEntity
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity

@Dao
interface RoundDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRound(round: RoundEntity): Long

    @Insert
    suspend fun insertTeam(team: TeamEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: RoundTeamCrossRefEntity)

    @Transaction
    suspend fun insertRoundWithTeams(round: RoundEntity, teams: List<TeamEntity>): Long {
        closeOpenRoundsByGroup(round.groupId)
        val roundId = insertRound(round)
        for (team in teams) {
            val teamId = insertTeam(TeamEntity(name = team.name))
            insertCrossRef(RoundTeamCrossRefEntity(roundId, teamId))
        }
        return roundId
    }

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTeams(teams: List<TeamEntity>): List<Long>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCrossRef(crossRef: RoundTeamCrossRefEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCrossRefs(crossRefs: List<RoundTeamCrossRefEntity>)
//
//    @Query("SELECT * FROM rounds WHERE roundId = :id")
//    suspend fun getRoundById(id: Long): RoundEntity?
//
//    @Query("SELECT * FROM rounds WHERE groupId = :groupId AND opened = 1")
//    suspend fun getOpenedRoundByGroup(groupId: Long): RoundEntity?
//
//    @Query("SELECT * FROM rounds WHERE groupId = :groupId ORDER BY date DESC")
//    suspend fun getRoundsByGroup(groupId: Long): List<RoundEntity>

//    @Transaction
//    @Query("SELECT * FROM rounds WHERE roundId = :roundId")
//    suspend fun getRoundWithTeams(roundId: Long): RoundWithTeamsEntity

    @Transaction
    @Query("SELECT * FROM rounds WHERE groupId = :groupId AND opened = 1 LIMIT 1")
    suspend fun getRunningRound(groupId: Long): RoundWithTeamsEntity?

    @Query("UPDATE rounds SET opened = 0 WHERE groupId = :groupId AND opened = 1")
    suspend fun closeOpenRoundsByGroup(groupId: Long)
}