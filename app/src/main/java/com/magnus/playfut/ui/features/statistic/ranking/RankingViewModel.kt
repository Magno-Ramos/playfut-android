package com.magnus.playfut.ui.features.statistic.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.domain.repository.StatisticsRepository
import com.magnus.playfut.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RankingViewModel(
    private val repository: StatisticsRepository
) : ViewModel() {
    private val _artilleryState = MutableStateFlow<UiState<List<Artillery>>>(UiState.Loading)
    val artilleryState: StateFlow<UiState<List<Artillery>>> = _artilleryState

    fun fetchArtillery(groupId: String) {
        viewModelScope.launch {
            repository.fetchArtilleryRanking(groupId)
                .onFailure { _artilleryState.value = UiState.Error(it) }
                .onSuccess { _artilleryState.value = UiState.Success(it) }
        }
    }
}