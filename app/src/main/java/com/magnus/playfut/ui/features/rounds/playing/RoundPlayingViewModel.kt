package com.magnus.playfut.ui.features.rounds.playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.ui.domain.model.ui.RoundPlayingUiModel
import com.magnus.playfut.ui.domain.model.ui.toUiModel
import com.magnus.playfut.ui.domain.repository.RoundRepository
import com.magnus.playfut.ui.domain.repository.ScoreRepository
import com.magnus.playfut.ui.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundPlayingViewModel(
    private val repository: RoundRepository,
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    private val _roundState = MutableStateFlow<UiState<RoundPlayingUiModel>>(UiState.Loading)
    val roundState = _roundState.asStateFlow()

    fun fetchRunningRound(roundId: String) {
        viewModelScope.launch {
            runCatching {
                val details = repository.fetchRoundDetails(roundId).getOrThrow()
                val ranking = scoreRepository.fetchPlayerGoalsInRound(roundId).getOrThrow()
                val data = RoundPlayingUiModel(details.toUiModel(), ranking)
                _roundState.value = UiState.Success(data)
            }.onFailure {
                _roundState.value = UiState.Error(null)
            }
        }
    }
}