package com.magnus.playfut.ui.domain.repository.remote

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.datasource.GroupDataSource
import kotlinx.coroutines.flow.Flow

class RemoteGroupRepository (
    private val auth: FirebaseAuth
) : GroupDataSource {
    override suspend fun createGroup(name: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun editGroup(id: String, name: String): Result<Unit> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }
}