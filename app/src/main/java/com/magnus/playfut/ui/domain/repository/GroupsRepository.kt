package com.magnus.playfut.ui.domain.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.magnus.playfut.ui.domain.model.Group
import kotlinx.coroutines.tasks.await

class GroupsRepository {

    suspend fun createGroup(name: String) = runCatching {
        val user = FirebaseAuth.getInstance().currentUser ?: return@runCatching
        val firestore = Firebase.firestore

        val id = firestore
            .collection("users/${user.uid}/groups")
            .document().id // Gera o ID

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

    suspend fun fetchGroups(): List<Group> {
        val user = FirebaseAuth.getInstance().currentUser ?: return listOf()
        val snapshot = Firebase.firestore.document("users/${user.uid}").collection("groups").get().await()
        return snapshot.documents.mapNotNull {
            it.toObject(Group::class.java)
        }
    }
}