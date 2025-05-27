package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.magnus.playfut.ui.domain.database.entities.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.GroupWithPlayersAndRounds
import com.magnus.playfut.ui.domain.database.entities.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.RoundEntity

@Dao
interface GroupDao {
    @Insert
    suspend fun insertGroup(group: GroupEntity): Long

    @Insert
    suspend fun insertPlayers(players: List<PlayerEntity>)

    @Insert
    suspend fun insertRounds(rounds: List<RoundEntity>)

    @Update
    suspend fun updateGroup(group: GroupEntity)

    @Delete
    suspend fun deleteGroup(group: GroupEntity)

    @Transaction
    @Query("SELECT * FROM `groups`")
    suspend fun getAllGroupsWithDetails(): List<GroupWithPlayersAndRounds>

    @Transaction
    @Query("SELECT * FROM `groups` WHERE id = :groupId")
    suspend fun getGroupWithDetails(groupId: Long): GroupWithPlayersAndRounds
}
