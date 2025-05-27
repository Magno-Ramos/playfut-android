package com.magnus.playfut.ui.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.database.daos.GroupDao
import com.magnus.playfut.ui.domain.database.entities.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.toGroup
import com.magnus.playfut.ui.domain.model.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface GroupRepository {
    suspend fun createGroup(name: String): Result<Unit>

    fun observeGroups(): Flow<List<Group>>
}

class RemoteGroupRepository(
    private val auth: FirebaseAuth
) : GroupRepository {
    override suspend fun createGroup(name: String): Result<Unit> {
        // TODO, create group in Firebase
        return Result.success(Unit)
    }

    override fun observeGroups(): Flow<List<Group>> {
        // TODO, fetch groups from Firebase
        return flowOf(listOf())
    }
}

class LocalGroupRepository(
    private val dao: GroupDao
) : GroupRepository {
    override suspend fun createGroup(name: String) = runCatching {
        dao.insertGroup(GroupEntity(name = name))
    }

    override fun observeGroups(): Flow<List<Group>> {
        return dao.observeAllGroupsWithDetails().map { entities ->
            entities.map { it.toGroup() }
        }
    }
}