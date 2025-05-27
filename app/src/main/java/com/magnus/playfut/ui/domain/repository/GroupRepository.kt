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
    suspend fun createGroup(name: String): Result<String>

    suspend fun fetchGroup(groupId: String): Result<Group?>

    suspend fun deleteGroup(groupId: String): Result<Unit>

    fun observeGroup(groupId: String): Flow<Group?>

    fun observeGroups(): Flow<List<Group>>
}

class RemoteGroupRepository(
    private val auth: FirebaseAuth
) : GroupRepository {
    override suspend fun createGroup(name: String): Result<String> {
        // TODO, create group in Firebase
        return Result.success("1")
    }

    override suspend fun fetchGroup(groupId: String): Result<Group?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGroup(groupId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun observeGroup(groupId: String): Flow<Group?> {
        TODO("Not yet implemented")
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
        dao.insertGroup(GroupEntity(name = name)).toString()
    }

    override suspend fun fetchGroup(groupId: String) = runCatching {
        dao.getGroupById(groupId.toLong())?.toGroup()
    }

    override suspend fun deleteGroup(groupId: String) = runCatching {
        dao.deleteGroup(GroupEntity(id = groupId.toLong()))
    }

    override fun observeGroup(groupId: String): Flow<Group?> {
        return dao.observeGroupWithDetails(groupId.toLong()).map { it?.toGroup() }
    }

    override fun observeGroups(): Flow<List<Group>> {
        return dao.observeAllGroupsWithDetails().map { entities ->
            entities.map { it.toGroup() }
        }
    }
}