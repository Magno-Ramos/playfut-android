package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.domain.state.asSuccess
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingRoutes
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.ArtilleryRanking
import com.magnus.playfut.ui.features.rounds.playing.components.GradientButton
import com.magnus.playfut.ui.features.rounds.playing.components.MatchList
import com.magnus.playfut.ui.features.rounds.playing.components.TeamGroup
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayingHomeViewState
import com.magnus.playfut.ui.theme.spacing

@Composable
fun RoundPlayingHomeScreen(
    roundId: String,
    viewModel: RoundPlayingViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val roundState by viewModel.roundState.collectAsState()
    val closeRoundState by viewModel.closeRoundState.collectAsState()
    var title by remember { mutableStateOf("") }

    when (val state = closeRoundState) {
        UiState.Loading -> {
            // TODO
        }

        is UiState.Error -> {
            print(state.message)
        }

        is UiState.Success<*> -> {
            context.activity?.finish()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchRound(roundId)
    }

    fun onClickCloseRound() {
        roundState.asSuccess()?.data?.let {
            viewModel.closeRound(it.groupId)
        }
    }

    Scaffold(
        topBar = { AppBar(title = title) },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(MaterialTheme.spacing.medium)) {
                Button(
                    onClick = { onClickCloseRound() },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(MaterialTheme.spacing.medium),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(
                        text = "Finalizar Rodada",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            when (val state = roundState) {
                UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView("Desculpe, ocorreu um erro!")
                is UiState.Success<RoundPlayingHomeViewState> -> {
                    RoundPlayingHomeMenu(
                        viewState = state.data,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun RoundPlayingHomeMenu(
    viewState: RoundPlayingHomeViewState,
    navController: NavController
) {
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        GradientButton(
            text = "Adicionar Partida",
            subtext = "Qual time ganhará dessa vez?",
            onClick = { navController.navigate(RoundPlayingRoutes.Match.route) }
        )
        TeamGroup(
            teams = viewState.teams,
            onClickItem = { team -> TODO("ON CLICK OPEN TEAM") }
        )
        MatchList(matches = viewState.matches)
        ArtilleryRanking(artillery = viewState.artillery)
    }
}

@Composable
private fun AppBar(title: String) {
    val context = LocalContext.current
    AppToolbar(
        title = title,
        onClickBack = { context.activity?.finish() }
    )
}
