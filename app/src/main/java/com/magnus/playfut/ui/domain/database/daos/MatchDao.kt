package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.magnus.playfut.ui.domain.database.entities.relations.MatchWithScoresAndTeams
import com.magnus.playfut.ui.domain.database.entities.structure.MatchEntity
import com.magnus.playfut.ui.domain.database.entities.structure.ScoreEntity
import com.magnus.playfut.ui.domain.model.form.MatchForm

@Dao
interface MatchDao {
    @Transaction
    @Query("SELECT * FROM MatchEntity WHERE id = :matchId")
    suspend fun getMatchWithScores(matchId: Long): MatchWithScoresAndTeams?

    @Query("SELECT * FROM MatchEntity WHERE roundId = :roundId ORDER BY id ASC")
    suspend fun getMatchesForRound(roundId: String): List<MatchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: MatchEntity): Long

    @Transaction
    suspend fun insertMatchWithScores(form: MatchForm): Long {
        val match = MatchEntity(
            homeTeamId = form.homeTeamId.toLong(),
            awayTeamId = form.awayTeamId.toLong(),
            roundId = form.roundId.toLong()
        )

        val matchId = insertMatch(match)

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
        }

        return matchId
    }
}