package com.magnus.playfut.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.magnus.playfut.domain.database.daos.UserDao
import com.magnus.playfut.domain.database.entities.structure.UserEntity
import com.magnus.playfut.domain.model.structure.User
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao
) {

    var isProVersionEnabled = false

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
        var firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            // sign in anonymously
            val result = firebaseAuth.signInAnonymously().await()
            firebaseUser = result.user ?: error("Failed to sign in anonymously")
        }

        handleSignIn(firebaseUser)
    }

    private suspend fun handleSignIn(firebaseUser: FirebaseUser): User? {
        val firestore = FirebaseFirestore.getInstance()

        // get user from database, if it exists
        userDao.getUserByUid(firebaseUser.uid)?.let {
            isProVersionEnabled = it.isPro
        }

        val userDoc = firestore
            .collection("users")
            .document(firebaseUser.uid)

        var snapshot = userDoc.get().await()
        if (!snapshot.exists()) {
            val user = User(uid = firebaseUser.uid)
            userDoc.set(user).await()
            snapshot = userDoc.get().await()
        }

        val user = snapshot.toObject<User>()
        user?.let {
            isProVersionEnabled = it.isPro
            val entity = UserEntity(uid = it.uid, isPro = it.isPro)
            userDao.insert(entity)
        }

        return user
    }
}