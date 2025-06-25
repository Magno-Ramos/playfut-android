package com.magnus.playfut.domain.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoGroupWithOpenedRoundEntity
import com.magnus.playfut.domain.database.entities.relations.pojo.PojoGroupWithPlayersAndRoundsCount
import com.magnus.playfut.domain.database.entities.structure.GroupEntity
import com.magnus.playfut.domain.database.entities.structure.GroupStatsEntity

@Dao
interface GroupDao {
    @Insert
    suspend fun createGroup(group: GroupEntity): Long

    @Insert
    suspend fun createGroupStats(groupStats: GroupStatsEntity): Long

    @Transaction
    suspend fun insertGroup(group: GroupEntity): Long {
        val groupId = createGroup(group)
        return createGroupStats(GroupStatsEntity(groupId = groupId))
    }

    @Update
    suspend fun updateGroup(group: GroupEntity)

    @Query("SELECT* FROM `groups` WHERE groupId = :groupId")
    suspend fun getGroupById(groupId: Long): GroupEntity

    // get group stats
    @Query("SELECT * FROM group_stats WHERE groupId = :groupId")
    suspend fun getGroupStats(groupId: String): GroupStatsEntity

    @Transaction
    @Query(
        """
        SELECT g.*, r.*,
            (SELECT COUNT(playerId) FROM players WHERE groupOwnerId = g.groupId AND active = 1 AND type = 'MEMBER') as playerCount,
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
            (SELECT COUNT(playerId) FROM players WHERE groupOwnerId = g.groupId AND active = 1 AND type = 'MEMBER') as playerCount,
            (SELECT COUNT(roundId) FROM rounds WHERE groupOwnerId = g.groupId) as roundCount
        FROM `groups` as g
    """
    )
    suspend fun getAllGroupsWithCounts(): List<PojoGroupWithPlayersAndRoundsCount>

    //region delete
    @Query("DELETE FROM group_stats WHERE groupId = :groupId")
    suspend fun deleteGroupStats(groupId: String)

    @Query("DELETE FROM players WHERE groupOwnerId = :groupId")
    suspend fun deletePlayers(groupId: String)

    @Query("DELETE FROM rounds WHERE groupOwnerId = :groupId")
    suspend fun deleteRounds(groupId: String)

    @Query("DELETE FROM `groups` WHERE groupId = :groupId")
    suspend fun deleteGroup(groupId: String)

    @Transaction
    suspend fun deleteGroupAndAllRelated(groupId: String) {
        deleteGroupStats(groupId)
        deletePlayers(groupId)
        deleteRounds(groupId)
        deleteGroup(groupId)
    }
    //endregion
}
