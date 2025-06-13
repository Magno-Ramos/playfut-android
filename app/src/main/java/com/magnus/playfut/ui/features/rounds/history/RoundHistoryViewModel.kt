package com.magnus.playfut.ui.features.rounds.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.model.relations.RoundResult
import com.magnus.playfut.domain.repository.RoundRepository
import com.magnus.playfut.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoundHistoryViewModel (
    private val repository: RoundRepository
) : ViewModel() {

    private val _historyState = MutableStateFlow<UiState<List<RoundResult>>>(UiState.Loading)
    val historyState: StateFlow<UiState<List<RoundResult>>> = _historyState

    fun fetchHistory(groupId: String) {
        viewModelScope.launch {
            _historyState.value = UiState.Loading
            repository.getRoundResultList(groupId)
                .onFailure { _historyState.value = UiState.Error() }
                .onSuccess { _historyState.value = UiState.Success(it) }
        }
    }
}