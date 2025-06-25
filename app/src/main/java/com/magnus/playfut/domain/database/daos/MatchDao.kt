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

    @Transaction
    suspend fun insertMatchWithScores(form: MatchForm): Long {
        val round = getRoundById(form.roundId.toLong())
        val match = MatchEntity(
            homeTeamId = form.homeTeamId.toLong(),
            awayTeamId = form.awayTeamId.toLong(),
            roundId = form.roundId.toLong()
        )

        val matchId = insertMatch(match)

        // update group stats
        increaseGroupMatchCount(round.groupOwnerId)

        form.scores.forEach {
            insertScore(
                ScoreEntity(
                    matchId = matchId,
                    playerId = it.playerId.toLong(),
                    roundId = form.roundId.toLong(),
                    teamIdScored = it.scoredTeamId.toLong(),
                    isOwnGoal = it.isOwnGoal
                )
            )

            // update group stats
            increaseGroupScoreCount(round.groupOwnerId)
        }

        return matchId
    }
}