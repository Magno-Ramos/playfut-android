package com.magnus.playfut.ui.features.rounds.playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.domain.repository.RoundRepository
import com.magnus.playfut.ui.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundPlayingViewModel(
    private val repository: RoundRepository
) : ViewModel() {

    private val _roundState = MutableStateFlow<UiState<Round?>>(UiState.Loading)
    val roundState = _roundState.asStateFlow()

    fun fetchRunningRound(groupId: String) {
        viewModelScope.launch {
            repository.fetchRunningRound(groupId)
                .onFailure { _roundState.value = UiState.Error(null) }
                .onSuccess { _roundState.value = UiState.Success(it) }
        }
    }
}