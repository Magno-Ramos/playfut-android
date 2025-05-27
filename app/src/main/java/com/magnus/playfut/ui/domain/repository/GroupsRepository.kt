package com.magnus.playfut.ui.domain.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.magnus.playfut.ui.domain.database.AppDatabase
import com.magnus.playfut.ui.domain.database.entities.GroupEntity
import com.magnus.playfut.ui.domain.database.entities.toGroup
import com.magnus.playfut.ui.domain.model.Group
import kotlinx.coroutines.tasks.await

class GroupsRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun createGroup(name: String) = runCatching {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            appDatabase.groupDao().insertGroup(GroupEntity(name = name))
        } else {
            val firestore = Firebase.firestore

            val id = firestore
                .collection("users/${user.uid}/groups")
                .document().id

            val group = Group(
                id = id,
                name = name,
                players = listOf(),
                rounds = listOf()
            )

            firestore
                .collection("users/${user.uid}/groups")
                .document(id)
                .set(group)
                .await()
        }
    }

    suspend fun fetchGroups(): List<Group> {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            return appDatabase.groupDao().getAllGroupsWithDetails().map { it.toGroup() }
        } else {
            val snapshot = Firebase.firestore.document("users/${user.uid}").collection("groups").get().await()
            return snapshot.documents.mapNotNull {
                it.toObject(Group::class.java)
            }
        }
    }
}