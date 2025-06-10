package com.magnus.playfut.ui.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.relations.GroupWithOpenedRound
import com.magnus.playfut.ui.domain.model.relations.GroupWithPlayersAndRoundsCount
import com.magnus.playfut.ui.domain.model.structure.Group
import com.magnus.playfut.ui.domain.repository.datasource.GroupDataSource
import com.magnus.playfut.ui.domain.repository.local.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteGroupRepository

class GroupRepository(
    private val localRepository: LocalGroupRepository,
    private val remoteRepository: RemoteGroupRepository,
    private val auth: FirebaseAuth
) : GroupDataSource {

    private val source
        get() = if (auth.currentUser != null) remoteRepository else localRepository

    override suspend fun getGroupById(groupId: String): Result<Group?> {
        return source.getGroupById(groupId)
    }

    override suspend fun getGroupWithOpenedRound(groupId: String): Result<GroupWithOpenedRound?> {
        return source.getGroupWithOpenedRound(groupId)
    }

    override suspend fun getAllGroups(): Result<List<GroupWithPlayersAndRoundsCount>> {
        return source.getAllGroups()
    }

    override suspend fun createGroup(name: String): Result<String> {
        return source.createGroup(name)
    }

    override suspend fun editGroup(id: String, name: String): Result<Unit> {
        return source.editGroup(id, name)
    }

    override suspend fun deleteGroup(groupId: String): Result<Unit> {
        return source.deleteGroup(groupId)
    }
}