package com.magnus.playfut.ui.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.magnus.playfut.ui.domain.database.entities.relations.GroupWithOpenedRoundEntity
import com.magnus.playfut.ui.domain.database.entities.structure.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.structure.PlayerEntity
import com.magnus.playfut.ui.domain.database.entities.structure.RoundEntity

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

    @Query("SELECT * FROM `groups`")
    suspend fun getGroups(): List<GroupEntity>

    @Transaction
    @Query("SELECT g.*, r.* FROM `groups` g LEFT JOIN rounds r ON g.id = r.groupId AND r.opened = 1 WHERE g.id = :groupId LIMIT 1 ")
    suspend fun getGroupWithOpenedRound(groupId: Long): GroupWithOpenedRoundEntity?
}
