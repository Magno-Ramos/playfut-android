package com.magnus.playfut.ui.domain.repository.remote

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.datasource.GroupDataSource
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.GroupWithOpenedRound

class RemoteGroupRepository (
    private val auth: FirebaseAuth
) : GroupDataSource {
    override suspend fun getGroupWithOpenedRound(groupId: String): Result<GroupWithOpenedRound?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGroups(): Result<List<Group>> {
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