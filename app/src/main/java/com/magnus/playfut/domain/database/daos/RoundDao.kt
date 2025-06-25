package com.magnus.playfut.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.domain.database.entities.relations.crossref.CrossRefSchemaPlayer
import com.magnus.playfut.domain.database.entities.relations.crossref.CrossRefTeamRound
import com.magnus.playfut.domain.database.entities.relations.crossref.SchemaPlayerRole
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoRoundResultWithRoundAndTeam
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoRoundWithDetails
import com.magnus.playfut.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.domain.database.entities.structure.RoundResultEntity
import com.magnus.playfut.domain.database.entities.structure.SchemaEntity
import com.magnus.playfut.domain.database.entities.structure.TeamEntity
import com.magnus.playfut.domain.model.ui.TeamSchema

@Dao
interface RoundDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRound(round: RoundEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: TeamEntity): Long

    @Query("SELECT * FROM rounds WHERE roundId = :roundId")
    suspend fun getRoundById(roundId: Long): RoundEntity

    @Query("UPDATE rounds SET opened = 0 WHERE groupOwnerId = :groupId AND opened = 1")
    suspend fun closeOpenRoundsByGroup(groupId: Long)

    @Query("SELECT COUNT(*) FROM rounds WHERE groupOwnerId = :groupId")
    suspend fun getRoundCountByGroup(groupId: String): Int

    @Transaction
    @Query("SELECT * FROM rounds WHERE roundId = :roundId")
    suspend fun getRoundDetails(roundId: String): PojoRoundWithDetails

    @Transaction
    @Query("""
        SELECT rr.* FROM round_result_table rr
        LEFT JOIN rounds r ON rr.roundId = r.roundId
        WHERE r.groupOwnerId = :groupId
    """)
    suspend fun getRoundsByGroup(groupId: Long): List<PojoRoundResultWithRoundAndTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchema(schema: SchemaEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchemaPlayerCrossRef(refs: List<CrossRefSchemaPlayer>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamRoundCrossRef(ref: CrossRefTeamRound)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoundResult(result: RoundResultEntity)

    // increase group stats rounds count
    @Query("UPDATE group_stats SET totalRounds = totalRounds + 1 WHERE groupId = :groupId")
    suspend fun increaseGroupRoundsCount(groupId: Long)

    @Query("UPDATE rounds SET opened = 0 WHERE roundId = :roundId")
    suspend fun closeRound(roundId: Long)

    @Transaction
    suspend fun closeRoundAndAddResult(roundId: String, winnerTeamId: String?) {
        closeRound(roundId.toLong())
        val result = RoundResultEntity(roundId = roundId.toLong(), winnerTeamId = winnerTeamId?.toLong())
        insertRoundResult(result)
    }

    @Transaction
    suspend fun insertTeamsWithSchema(groupId: Long, schemas: List<TeamSchema>): Long {
        closeOpenRoundsByGroup(groupId)
        val roundId = insertRound(RoundEntity(groupOwnerId = groupId, opened = true))

        // update group stats
        increaseGroupRoundsCount(groupId)

        schemas.forEach { distributorTeamSchema ->
            val team = TeamEntity(name = distributorTeamSchema.teamName)
            val teamId = insertTeam(team)

            insertTeamRoundCrossRef(CrossRefTeamRound(roundId = roundId, teamId = teamId))

            val schema = SchemaEntity(teamId = teamId, roundId = roundId)
            val schemaId = insertSchema(schema)

            val allPlayersRef = distributorTeamSchema.goalKeepers.map {
                CrossRefSchemaPlayer(
                    schemaId = schemaId,
                    playerId = it.id.toLong(),
                    role = SchemaPlayerRole.GOALKEEPER
                )
            } + distributorTeamSchema.startPlaying.map {
                CrossRefSchemaPlayer(
                    schemaId = schemaId,
                    playerId = it.id.toLong(),
                    role = SchemaPlayerRole.START_PLAYING
                )
            } + distributorTeamSchema.substitutes.map {
                CrossRefSchemaPlayer(
                    schemaId = schemaId,
                    playerId = it.id.toLong(),
                    role = SchemaPlayerRole.SUBSTITUTE
                )
            }

            insertSchemaPlayerCrossRef(allPlayersRef)
        }
        return roundId
    }
}