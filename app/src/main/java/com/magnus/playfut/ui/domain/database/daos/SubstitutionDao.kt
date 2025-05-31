package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magnus.playfut.ui.domain.database.entities.SubstitutionEntryEntity

@Dao
interface SubstitutionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubstitution(entry: SubstitutionEntryEntity): Long

    @Query("SELECT * FROM substitutions WHERE roundId = :roundId")
    suspend fun getSubstitutionsByRound(roundId: Long): List<SubstitutionEntryEntity>

    @Query("SELECT * FROM substitutions WHERE teamInRoundId = :teamInRoundId")
    suspend fun getSubstitutionsByTeam(teamInRoundId: Long): List<SubstitutionEntryEntity>
}