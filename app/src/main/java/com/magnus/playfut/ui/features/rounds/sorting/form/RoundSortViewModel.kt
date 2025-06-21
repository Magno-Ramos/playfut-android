package com.magnus.playfut.ui.features.rounds.sorting.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnus.playfut.domain.helper.DistributionType
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.ui.TeamSchema
import com.magnus.playfut.domain.repository.PlayerRepository
import com.magnus.playfut.domain.repository.PreferencesRepository
import com.magnus.playfut.domain.repository.RoundRepository
import com.magnus.playfut.domain.state.ActionResultState
import com.magnus.playfut.domain.state.UiState
import com.magnus.playfut.ui.features.rounds.sorting.form.model.SelectablePlayer
import com.magnus.playfut.ui.features.rounds.sorting.form.model.toSelectablePlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoundSortViewModel(
    private val playerRepository: PlayerRepository,
    private val roundRepository: RoundRepository,
    private val prefsRepository: PreferencesRepository
) : ViewModel() {
    private var playersLoaded: Boolean = false

    private val _playersState = MutableStateFlow<UiState<List<Player>>>(UiState.Loading)
    val playersState = _playersState.asStateFlow()

    private val _selectablePlayers = MutableStateFlow<List<SelectablePlayer>>(emptyList())
    val selectablePlayers = _selectablePlayers.asStateFlow()

    private val _createRoundState = MutableStateFlow<ActionResultState<Long>>(ActionResultState.Idle)
    val createRoundState = _createRoundState.asStateFlow()

    val teamsCount = prefsRepository.getTeamsCount()
    val playersCount = prefsRepository.getPlayersCount()
    val distributionType = prefsRepository.getDistributionType()

    var groupId: String = ""
    var editableTeam: TeamSchema? = null
    var teamSchema: List<TeamSchema>? = null

    fun updateTeamsCount(count: Int?) {
        viewModelScope.launch {
            prefsRepository.saveInputTeamsCount(count)
        }
    }

    fun updatePlayersCount(count: Int?) {
        viewModelScope.launch {
            prefsRepository.saveInputPlayersCount(count)
        }
    }

    fun updateDistributionType(type: DistributionType) {
        viewModelScope.launch {
            prefsRepository.saveDistributionType(type)
        }
    }

    fun toggleSelection(player: SelectablePlayer) {
        _selectablePlayers.value = _selectablePlayers.value.map {
            if (it.id == player.id) {
                it.copy(selected = !it.selected)
            } else {
                it
            }
        }
    }

    fun fetchPlayers(groupId: String) {
        if (playersLoaded) {
            return
        }

        viewModelScope.launch {
            _playersState.value = UiState.Loading
            playerRepository.fetchPlayers(groupId)
                .onFailure { _playersState.value = UiState.Error() }
                .onSuccess { players ->
                    _playersState.value = UiState.Success(players)
                    _selectablePlayers.value = players.map { it.toSelectablePlayer() }
                    playersLoaded = true
                }
        }
    }

    fun createRound() {
        viewModelScope.launch {
            _createRoundState.value = ActionResultState.Loading
            val mTeams = teamSchema

            if (mTeams.isNullOrEmpty()) {
                _createRoundState.value = ActionResultState.Error("No teams selected")
                return@launch
            }

            roundRepository.createRound(groupId, mTeams)
                .onFailure { _createRoundState.value = ActionResultState.Error(it.message) }
                .onSuccess { _createRoundState.value = ActionResultState.Success(it) }
        }
    }
}