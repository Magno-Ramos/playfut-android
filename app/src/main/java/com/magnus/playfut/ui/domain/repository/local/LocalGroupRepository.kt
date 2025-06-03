package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.GroupDao
import com.magnus.playfut.ui.domain.database.entities.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.toGroup
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.datasource.GroupDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalGroupRepository (
    private val dao: GroupDao
) : GroupDataSource {

    override suspend fun createGroup(name: String) = runCatching {
        dao.insertGroup(GroupEntity(name = name)).toString()
    }

    override suspend fun editGroup(id: String, name: String): Result<Unit> = runCatching {
        dao.updateGroup(GroupEntity(id = id.toLong(), name = name))
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