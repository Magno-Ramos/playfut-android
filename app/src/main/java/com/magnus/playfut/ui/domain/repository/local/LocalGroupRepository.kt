package com.magnus.playfut.ui.domain.repository.local

import com.magnus.playfut.ui.domain.database.daos.GroupDao
import com.magnus.playfut.ui.domain.database.entities.relations.toGroupWithOpenedRound
import com.magnus.playfut.ui.domain.database.entities.structure.GroupEntity
import com.magnus.playfut.ui.domain.datasource.GroupDataSource
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.GroupWithOpenedRound
import com.magnus.playfut.ui.domain.model.toGroup

class LocalGroupRepository (
    private val dao: GroupDao
) : GroupDataSource {
    override suspend fun getGroupWithOpenedRound(groupId: String): Result<GroupWithOpenedRound?> = runCatching {
        dao.getGroupWithOpenedRound(groupId.toLong())?.toGroupWithOpenedRound()
    }

    override suspend fun getAllGroups(): Result<List<Group>> = runCatching {
        dao.getAllGroupsWithCounts().map { it.toGroup() }
    }

    override suspend fun createGroup(name: String) = runCatching {
        dao.insertGroup(GroupEntity(name = name)).toString()
    }

    override suspend fun editGroup(id: String, name: String): Result<Unit> = runCatching {
        dao.updateGroup(GroupEntity(id = id.toLong(), name = name))
    }

    override suspend fun deleteGroup(groupId: String) = runCatching {
        dao.deleteGroup(GroupEntity(id = groupId.toLong()))
    }
}