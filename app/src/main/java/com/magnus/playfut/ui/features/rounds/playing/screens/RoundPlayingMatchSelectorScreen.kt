package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.magnus.playfut.domain.state.asSuccess
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingRoutes
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.TeamSelector
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import com.magnus.playfut.ui.theme.spacing

@Composable
fun RoundPlayingMatchSelectorScreen(
    viewModel: RoundPlayingViewModel,
    navController: NavController
) {
    val roundState by viewModel.roundState.collectAsState()
    val teams = roundState.asSuccess()?.data?.teams.orEmpty()

    var homeSelected by remember { mutableStateOf<RoundTeamItem?>(null) }
    var awaySelected by remember { mutableStateOf<RoundTeamItem?>(null) }

    val homeOptions = teams.filter { it != homeSelected }.map { it.name to it.id }
    val awayOptions = teams.filter { it != awaySelected }.map { it.name to it.id }

    val isContinueEnabled = homeSelected != null && awaySelected != null && homeSelected != awaySelected

    fun onSelectHomeTeam(id: String) {
        homeSelected = teams.first { it.id == id }
        viewModel.matchHomeTeam = homeSelected
    }

    fun onSelectAwayTeam(id: String) {
        awaySelected = teams.first { it.id == id }
        viewModel.matchAwayTeam = awaySelected
    }

    fun onClickContinue() {
        viewModel.matchHomeTeam = homeSelected
        viewModel.matchAwayTeam = awaySelected
        navController.navigate(RoundPlayingRoutes.Match.route)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Confronto",
                onClickBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Button(
                    enabled = isContinueEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { onClickContinue() }
                ) {
                    Text(text = "Seguir para Partida")
                }
            }
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
                .padding(MaterialTheme.spacing.medium)
        ) {
            TeamSelector(
                homeTeam = homeSelected,
                awayTeam = awaySelected,
                homeOptions = homeOptions,
                awayOptions = awayOptions,
                onSelectHomeTeam = ::onSelectHomeTeam,
                onSelectAwayTeam = ::onSelectAwayTeam
            )
        }
    }
}