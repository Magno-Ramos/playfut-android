package com.magnus.playfut.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.model.structure.User
import com.magnus.playfut.domain.repository.UserRepository
import com.magnus.playfut.domain.state.UiState
import com.magnus.playfut.domain.state.collectUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<UiState<User?>>(UiState.Loading)
    val authState: StateFlow<UiState<User?>> = _authState

    fun signInAnonymously() {
        viewModelScope.launch {
            userRepository
                .signInAnonymously()
                .collectUiState(_authState)
        }
    }
}