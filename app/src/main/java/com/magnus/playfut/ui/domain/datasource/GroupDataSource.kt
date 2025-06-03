package com.magnus.playfut.ui.domain.datasource

import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.GroupWithOpenedRound

interface GroupDataSource {
    suspend fun getGroupWithOpenedRound(groupId: String): Result<GroupWithOpenedRound?>

    suspend fun getAllGroups(): Result<List<Group>>

    suspend fun createGroup(name: String): Result<String>

    suspend fun editGroup(id: String, name: String): Result<Unit>

    suspend fun deleteGroup(groupId: String): Result<Unit>
}