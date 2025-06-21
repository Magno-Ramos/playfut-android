package com.magnus.playfut.ui.features.statistic.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.repository.StatisticsRepository
import com.magnus.playfut.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatisticHomeViewModel(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<StatisticHomeViewState>>(UiState.Loading)
    val uiState: StateFlow<UiState<StatisticHomeViewState>> = _uiState

    fun fetchStatistics(groupId: String) {
        viewModelScope.launch {
            statisticsRepository.fetchStatistics(groupId)
                .onFailure { _uiState.value = UiState.Error() }
                .onSuccess { _uiState.value = UiState.Success(it) }
        }
    }
}