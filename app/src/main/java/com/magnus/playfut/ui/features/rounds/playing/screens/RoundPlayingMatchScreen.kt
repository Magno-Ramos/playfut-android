package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.magnus.playfut.ui.domain.state.asSuccess
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.FinishMatch
import com.magnus.playfut.ui.features.rounds.playing.components.GoalRegisterForm
import com.magnus.playfut.ui.features.rounds.playing.components.GradientAppBar
import com.magnus.playfut.ui.features.rounds.playing.components.MatchScore
import com.magnus.playfut.ui.features.rounds.playing.components.Stopwatch
import com.magnus.playfut.ui.features.rounds.playing.components.TeamSelector
import com.magnus.playfut.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoundPlayingMatchScreen(
    viewModel: RoundPlayingViewModel = koinViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val roundState by viewModel.roundState.collectAsState()

    val teams =  roundState.asSuccess()?.data?.teams.orEmpty()
    val players = roundState.asSuccess()?.data?.players.orEmpty()

    var homeSelected by remember { mutableStateOf(teams[0]) }
    var awaySelected by remember { mutableStateOf(teams[1]) }

    var homeScore by remember { mutableIntStateOf(0) }
    var awayScore by remember { mutableIntStateOf(0) }

    val homeOptions = teams.filter { it != homeSelected }.map { it.name to it.id }
    val awayOptions = teams.filter { it != awaySelected }.map { it.name to it.id }

    fun onSelectHomeTeam(id: String) {
        homeSelected = teams.first { it.id == id }
    }

    fun onSelectAwayTeam(id: String) {
        awaySelected = teams.first { it.id == id }
    }

    fun onClickFinishMatch() {

    }

    Scaffold(
        topBar = {
            GradientAppBar {
                navController.popBackStack()
            }
        }
    ) { paddings ->
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .padding(paddings)
                .padding(MaterialTheme.spacing.medium)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            TeamSelector(
                homeTeam = homeSelected,
                awayTeam = awaySelected,
                homeOptions = homeOptions,
                awayOptions = awayOptions,
                onSelectHomeTeam = ::onSelectHomeTeam,
                onSelectAwayTeam = ::onSelectAwayTeam
            )
            Stopwatch()
            MatchScore()
            GoalRegisterForm(
                players = players,
                teams = teams
            )
            FinishMatch(onClickFinish = ::onClickFinishMatch)
        }
    }
}