package com.magnus.playfut.domain.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoGroupWithOpenedRoundEntity
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoGroupWithPlayersAndRoundsCount
import com.magnus.playfut.domain.database.entities.structure.GroupEntity

@Dao
interface GroupDao {
    @Insert
    suspend fun insertGroup(group: GroupEntity): Long

    @Update
    suspend fun updateGroup(group: GroupEntity)

    @Delete
    suspend fun deleteGroup(group: GroupEntity)

    @Query("SELECT* FROM `groups` WHERE groupId = :groupId")
    suspend fun getGroupById(groupId: Long): GroupEntity?

    @Transaction
    @Query(
        """
        SELECT g.*, r.*,
            (SELECT COUNT(playerId) FROM players WHERE groupOwnerId = g.groupId) as playerCount,
            (SELECT COUNT(roundId) FROM rounds WHERE groupOwnerId = g.groupId) as roundCount
        FROM `groups` g LEFT JOIN rounds r ON g.groupId = r.groupOwnerId AND r.opened = 1 WHERE g.groupId = :groupId LIMIT 1
    """
    )
    suspend fun getGroupWithOpenedRound(groupId: Long): PojoGroupWithOpenedRoundEntity?

    @Transaction
    @Query(
        """
        SELECT 
            g.*,
            (SELECT COUNT(playerId) FROM players WHERE groupOwnerId = g.groupId) as playerCount,
            (SELECT COUNT(roundId) FROM rounds WHERE groupOwnerId = g.groupId) as roundCount
        FROM `groups` as g
    """
    )
    suspend fun getAllGroupsWithCounts(): List<PojoGroupWithPlayersAndRoundsCount>
}
