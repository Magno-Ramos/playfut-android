package com.magnus.playfut.ui.domain.datasource

import com.magnus.playfut.ui.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupDataSource {
    suspend fun createGroup(name: String): Result<String>

    suspend fun editGroup(id: String, name: String): Result<Unit>

    suspend fun fetchGroup(groupId: String): Result<Group?>

    suspend fun deleteGroup(groupId: String): Result<Unit>

    fun observeGroup(groupId: String): Flow<Group?>

    fun observeGroups(): Flow<List<Group>>
}