package com.magnus.playfut.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.domain.database.entities.structure.RoundEntity
import com.magnus.playfut.domain.database.entities.structure.ScoreEntity
import com.magnus.playfut.domain.model.form.MatchForm

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches WHERE roundId = :roundId ORDER BY matchId ASC")
    suspend fun getMatchesForRound(roundId: String): List<MatchEntity>

    @Query("SELECT * FROM rounds WHERE roundId = :roundId")
    suspend fun getRoundById(roundId: Long): RoundEntity

    // get schema

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: MatchEntity): Long

    // increase group stats total match count
    @Query("UPDATE group_stats SET totalMatches = totalMatches + 1 WHERE groupId = :groupId")
    suspend fun increaseGroupMatchCount(groupId: Long)

    // increate group stats total scores count
    @Query("UPDATE group_stats SET totalScores = totalScores + 1 WHERE groupId = :groupId")
    suspend fun increaseGroupScoreCount(groupId: Long)

    // increase player stats total goals
    @Query("UPDATE player_stats SET goals = goals + 1 WHERE playerId = :playerId")
    suspend fun increasePlayerGoalCount(playerId: Long)

    // increate player stats matches count
    @Query("UPDATE player_stats SET matches = matches + 1 WHERE playerId = :playerId")
    suspend fun increasePlayerMatchesCount(playerId: Long)

    // increate player stats wins count
    @Query("UPDATE player_stats SET wins = wins + 1 WHERE playerId = :playerId")
    suspend fun increasePlayerWinsCount(playerId: Long)

    @Transaction
    suspend fun insertMatchWithScores(form: MatchForm): Long {
        val groupId = form.groupId.toLong()
        val homeScores = form.scores.filter { it.scoredTeamId == form.homeTeamId }
        val awayScores = form.scores.filter { it.scoredTeamId == form.awayTeamId }

        val match = MatchEntity(
            homeTeamId = form.homeTeamId.toLong(),
            awayTeamId = form.awayTeamId.toLong(),
            homeTeamScore = homeScores.size,
            awayTeamScore = awayScores.size,
            groupId = form.groupId.toLong(),
            roundId = form.roundId.toLong()
        )

        val matchId = insertMatch(match)

        // update group stats
        increaseGroupMatchCount(groupId)

        // update player stats
        val allPlayers = form.awayPlayers + form.homePlayers
        allPlayers.forEach { player ->
            increasePlayerMatchesCount(player.id.toLong())
        }

        when {
            // increase winner team count
            match.homeTeamScore > match.awayTeamScore -> {
                form.homePlayers.forEach {
                    increasePlayerWinsCount(it.id.toLong())
                }
            }

            // increase winner team count
            match.homeTeamScore < match.awayTeamScore -> {
                form.awayPlayers.forEach {
                    increasePlayerWinsCount(it.id.toLong())
                }
            }
        }

        form.scores.forEach {
            insertScore(
                ScoreEntity(
                    matchId = matchId,
                    playerId = it.playerId.toLong(),
                    roundId = form.roundId.toLong(),
                    teamIdScored = it.scoredTeamId.toLong(),
                    groupId = groupId,
                    isOwnGoal = it.isOwnGoal
                )
            )

            // update group stats
            increaseGroupScoreCount(groupId)

            // update player stats
            increasePlayerGoalCount(it.playerId.toLong())
        }

        return matchId
    }
}