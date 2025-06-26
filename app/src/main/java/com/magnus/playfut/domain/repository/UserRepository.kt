package com.magnus.playfut.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.magnus.playfut.domain.model.structure.User
import com.magnus.playfut.domain.model.structure.UserAnonymously
import com.magnus.playfut.domain.model.structure.UserRegistered
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firebaseAuth: FirebaseAuth
) {

    /**
     * Sign in anonymously.
     *
     * If the user is already signed in, it will be used.
     *
     * Otherwise, a new user will be created.
     *
     * @return [User]
     * @throws Exception if the sign in fails
     */
    suspend fun signInAnonymously(): Result<User?> = runCatching {
        val firestore = FirebaseFirestore.getInstance()

        val userDoc: DocumentReference
        val firebaseUser: FirebaseUser

        if (firebaseAuth.currentUser != null) {
            firebaseUser = firebaseAuth.currentUser ?: error("Failed to sign in anonymously")
            userDoc = firestore.collection("users").document(firebaseUser.uid)
        } else {
            val result = firebaseAuth.signInAnonymously().await()
            firebaseUser = result.user ?: error("Failed to sign in anonymously")
            userDoc = firestore.collection("users").document(firebaseUser.uid)
        }

        var snapshot = userDoc.get().await()
        if (!snapshot.exists()) {
            val isPro = false
            val firstAccess = FieldValue.serverTimestamp()
            val data = mapOf("isPro" to isPro, "firstAccess" to firstAccess)
            userDoc.set(data).await()
            snapshot = userDoc.get().await()
        }

        if (firebaseUser.isAnonymous) {
            snapshot.toObject<UserAnonymously>()
        } else {
            snapshot.toObject<UserRegistered>()
        }
    }
}