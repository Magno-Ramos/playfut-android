package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.ui.domain.database.entities.relations.PojoRoundWithDetails
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

    @Query("UPDATE rounds SET opened = 0 WHERE groupId = :groupId AND opened = 1")
    suspend fun closeOpenRoundsByGroup(groupId: Long)

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

    @Transaction
    @Query("SELECT * FROM rounds WHERE groupId = :groupId AND opened = 1 LIMIT 1")
    suspend fun getRunningRound(groupId: Long): RoundWithTeamsEntity?

    @Transaction
    @Query("SELECT * FROM rounds WHERE roundId = :roundId")
    suspend fun getRoundDetails(roundId: Long): PojoRoundWithDetails?
}