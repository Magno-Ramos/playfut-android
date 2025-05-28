package com.magnus.playfut.ui.features.player.create

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.domain.repository.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.RemoteGroupRepository

class PlayerCreateViewModel(
    private val remoteRepository: RemoteGroupRepository,
    private val localRepository: LocalGroupRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    fun createPlayer(
        groupId: String,
        playerName: String,
        playerType: PlayerType,
        playerQuality: Int
    ) {
        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        
    }
}