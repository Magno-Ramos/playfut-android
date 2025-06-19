package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.model.relations.GroupWithOpenedRound
import com.magnus.playfut.domain.model.relations.GroupWithPlayersAndRoundsCount
import com.magnus.playfut.domain.model.structure.Group

interface GroupDataSource {
    suspend fun getGroupById(groupId: String): Result<Group>

    suspend fun getGroupWithOpenedRound(groupId: String): Result<GroupWithOpenedRound>

    suspend fun getAllGroups(): Result<List<GroupWithPlayersAndRoundsCount>>

    suspend fun createGroup(name: String): Result<String>

    suspend fun editGroup(id: String, name: String): Result<Unit>

    suspend fun deleteGroup(groupId: String): Result<Unit>
}