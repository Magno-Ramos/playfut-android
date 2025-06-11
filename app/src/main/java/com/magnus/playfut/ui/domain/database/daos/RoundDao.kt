package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.ui.domain.database.entities.relations.crossref.CrossRefSchemaPlayer
import com.magnus.playfut.ui.domain.database.entities.relations.crossref.CrossRefTeamRound
import com.magnus.playfut.ui.domain.database.entities.relations.crossref.SchemaPlayerRole
import com.magnus.playfut.ui.domain.database.entities.relations.pojo.PojoRoundWithDetails
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.SchemaEntity
import com.magnus.playfut.ui.domain.database.entities.structure.TeamEntity
import com.magnus.playfut.ui.domain.helper.DistributorTeamSchema

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

    @Transaction
    @Query("SELECT * FROM rounds WHERE roundId = :roundId")
    suspend fun getRoundDetails(roundId: String): PojoRoundWithDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchema(schema: SchemaEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchemaPlayerCrossRef(refs: List<CrossRefSchemaPlayer>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamRoundCrossRef(ref: CrossRefTeamRound)

    @Query("UPDATE rounds SET opened = 0 WHERE roundId = :roundId")
    suspend fun closeRound(roundId: Long)

    @Transaction
    suspend fun insertTeamsWithSchema(groupId: Long, schemas: List<DistributorTeamSchema>): Long {
        closeOpenRoundsByGroup(groupId)
        val roundId = insertRound(RoundEntity(groupOwnerId = groupId, opened = true))

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