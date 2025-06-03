package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.common.MenuItem
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.TeamGroup
import com.magnus.playfut.ui.theme.spacing

@Composable
fun RoundPlayingHomeScreen(
    groupId: String,
    viewModel: RoundPlayingViewModel
) {
    val roundState by viewModel.roundState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRunningRound(groupId)
    }

    Scaffold(
        topBar = { AppBar() }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            when (val state = roundState) {
                UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView("Desculpe, ocorreu um erro!")
                is UiState.Success<Round> -> RoundPlayingHomeMenu(state.data)
            }
        }
    }
}

@Composable
private fun RoundPlayingHomeMenu(round: Round) {
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        MenuItem(
            icon = Icons.Default.Flag,
            title = "Adicionar Partida",
            subtitle = "Qual time ganhará dessa vez?",
            isPrimary = true
        )

        TeamGroup(teams = round.teams)
    }
}

@Composable
private fun AppBar() {
    val context = LocalContext.current
    AppToolbar(
        title = "Rodada",
        onClickBack = { context.activity?.finish() }
    )
}