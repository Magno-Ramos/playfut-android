package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.magnus.playfut.ui.domain.database.entities.PlayerEntity

@Dao
interface PlayerDao {

    @Insert
    suspend fun insertPlayer(player: PlayerEntity)

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Delete
    suspend fun deletePlayer(player: PlayerEntity)

    @Query("SELECT * FROM players WHERE groupId = :groupId")
    suspend fun getPlayersByGroupId(groupId: Long): List<PlayerEntity>
}