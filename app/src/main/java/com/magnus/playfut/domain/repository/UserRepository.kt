package com.magnus.playfut.domain.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.magnus.playfut.domain.database.daos.UserDao
import com.magnus.playfut.domain.database.entities.structure.UserEntity
import com.magnus.playfut.domain.model.structure.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao
) {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val firestore = FirebaseFirestore.getInstance()

    var isProVersionEnabled = false

    init {
        firebaseAuth.addAuthStateListener { state ->
            val firebaseUser = state.currentUser
            if (firebaseUser != null) {
                handleSignIn(firebaseUser)
            } else {
                handleSignOut()
            }
        }
    }

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
    suspend fun signInAnonymously(): Result<String?> = runCatching {
        var firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            // sign in anonymously
            val result = firebaseAuth.signInAnonymously().await()
            firebaseUser = result.user ?: error("Failed to sign in anonymously")
        }

        firebaseUser.uid
    }

    /**
     * Handle sign in.
     *
     * Control the flow of the user storage data
     *
     * @param firebaseUser [FirebaseUser]
     * @return [Unit]
     */
    private fun handleSignIn(firebaseUser: FirebaseUser) {
        val userDoc = firestore
            .collection("users")
            .document(firebaseUser.uid)

        scope.launch {
            val snapshot = userDoc.get().await()

            // if the user document does not exist, create it
            if (!snapshot.exists()) {
                val user = User(uid = firebaseUser.uid)
                userDoc.set(user).await()
            }
        }

        userDoc.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            // read user data from firestore
            value?.toObject<User>()?.let {
                scope.launch {
                    // update isProVersionEnabled
                    isProVersionEnabled = it.isPro

                    // update user storage data
                    val entity = UserEntity(uid = it.uid, isPro = it.isPro)
                    userDao.insert(entity)
                }
            }
        }
    }

    /**
     * Handle sign out.
     *
     * Clear the user storage data
     */
    private fun handleSignOut() {
        scope.launch {
            userDao.deleteAll()
            isProVersionEnabled = false
        }
    }
}