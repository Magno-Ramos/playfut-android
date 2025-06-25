package com.magnus.playfut.domain.repository.local

import com.magnus.playfut.domain.database.daos.GroupDao
import com.magnus.playfut.domain.database.entities.structure.GroupEntity
import com.magnus.playfut.domain.model.relations.GroupWithOpenedRound
import com.magnus.playfut.domain.model.relations.GroupWithPlayersAndRoundsCount
import com.magnus.playfut.domain.model.relations.toGroup
import com.magnus.playfut.domain.model.relations.toGroupWithOpenedRound
import com.magnus.playfut.domain.model.structure.Group
import com.magnus.playfut.domain.model.structure.toGroup
import com.magnus.playfut.domain.repository.datasource.GroupDataSource
import com.magnus.playfut.domain.repository.exceptions.GroupNotFoundException

class LocalGroupRepository (
    private val dao: GroupDao
) : GroupDataSource {
    override suspend fun getGroupById(groupId: String): Result<Group> {
        return runCatching { dao.getGroupById(groupId.toLong()).toGroup() }
    }

    override suspend fun getGroupWithOpenedRound(groupId: String): Result<GroupWithOpenedRound> = runCatching {
        val groupEntity = dao.getGroupWithOpenedRound(groupId.toLong()) ?: throw GroupNotFoundException()
        groupEntity.toGroupWithOpenedRound()
    }

    override suspend fun getAllGroups(): Result<List<GroupWithPlayersAndRoundsCount>> = runCatching {
        dao.getAllGroupsWithCounts().map { it.toGroup() }
    }

    override suspend fun createGroup(name: String) = runCatching {
        dao.insertGroup(GroupEntity(name = name)).toString()
    }

    override suspend fun editGroup(id: String, name: String): Result<Unit> = runCatching {
        dao.updateGroup(GroupEntity(groupId = id.toLong(), name = name))
    }

    override suspend fun deleteGroup(groupId: String) = runCatching {
        dao.deleteGroupAndAllRelated(groupId)
    }
}