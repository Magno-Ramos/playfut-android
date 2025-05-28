package com.magnus.playfut.ui.features.player.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.domain.repository.LocalPlayerRepository
import com.magnus.playfut.ui.domain.repository.RemotePlayerRepository
import com.magnus.playfut.ui.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerCreateViewModel(
    private val remoteRepository: RemotePlayerRepository,
    private val localRepository: LocalPlayerRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _createPlayerResult = MutableStateFlow<ActionResultState<Unit>>(ActionResultState.Idle)
    val createPlayerResult: StateFlow<ActionResultState<Unit>> = _createPlayerResult

    fun createPlayer(
        groupId: String,
        name: String,
        type: PlayerType,
        quality: Int
    ) {
        val repo = if (auth.currentUser != null) remoteRepository else localRepository
        viewModelScope.launch {
            _createPlayerResult.value = ActionResultState.Loading
            repo.createPlayer(groupId, name, type, quality)
                .onSuccess { _createPlayerResult.value = ActionResultState.Success(Unit) }
                .onFailure { _createPlayerResult.value = ActionResultState.Error(null) }
        }
    }
}