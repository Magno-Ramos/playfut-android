package com.magnus.playfut.ui.features.rounds.playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.model.ui.RoundDetailsUiModel
import com.magnus.playfut.ui.domain.model.ui.toUiModel
import com.magnus.playfut.ui.domain.repository.RoundRepository
import com.magnus.playfut.ui.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundPlayingViewModel(
    private val repository: RoundRepository
) : ViewModel() {

    private val _roundState = MutableStateFlow<UiState<RoundDetailsUiModel>>(UiState.Loading)
    val roundState = _roundState.asStateFlow()

    fun fetchRunningRound(roundId: String) {
        viewModelScope.launch {

            repository.fetchRoundDetails(roundId)
                .onFailure { _roundState.value = UiState.Error(null) }
                .onSuccess { round -> _roundState.value = UiState.Success(round.toUiModel()) }
        }
    }
}