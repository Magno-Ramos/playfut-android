package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.lifecycle.ViewModel
import com.magnus.playfut.ui.domain.model.structure.Schema
import com.magnus.playfut.ui.domain.repository.TeamRepository
import com.magnus.playfut.ui.domain.state.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoundPlayingTeamViewModel(
    private val repository: TeamRepository
) : ViewModel() {

    private val _teamState = MutableStateFlow<ActionResultState<Schema>>(ActionResultState.Idle)
    val teamState: StateFlow<ActionResultState<Schema>> = _teamState.asStateFlow()

    fun fetchTeam(teamId: String) {

    }
}