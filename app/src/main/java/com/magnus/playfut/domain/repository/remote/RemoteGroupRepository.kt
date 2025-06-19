package com.magnus.playfut.domain.repository.remote

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.domain.model.relations.GroupWithOpenedRound
import com.magnus.playfut.domain.model.relations.GroupWithPlayersAndRoundsCount
import com.magnus.playfut.domain.model.structure.Group
import com.magnus.playfut.domain.repository.datasource.GroupDataSource

class RemoteGroupRepository (
    private val auth: FirebaseAuth
) : GroupDataSource {
    override suspend fun getGroupById(groupId: String): Result<Group> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupWithOpenedRound(groupId: String): Result<GroupWithOpenedRound> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGroups(): Result<List<GroupWithPlayersAndRoundsCount>> {
        TODO("Not yet implemented")
    }

    override suspend fun createGroup(name: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun editGroup(id: String, name: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGroup(groupId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}