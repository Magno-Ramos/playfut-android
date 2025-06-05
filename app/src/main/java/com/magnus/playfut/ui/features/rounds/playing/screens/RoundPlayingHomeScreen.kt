package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.magnus.playfut.ui.domain.model.ui.RoundDetailsUiModel
import com.magnus.playfut.ui.domain.model.ui.TeamUiModel
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingRoutes
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.GradientButton
import com.magnus.playfut.ui.features.rounds.playing.components.MatchList
import com.magnus.playfut.ui.features.rounds.playing.components.TeamGroup
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun RoundPlayingHomeScreen(
    roundId: String,
    viewModel: RoundPlayingViewModel,
    navController: NavController
) {
    val roundState by viewModel.roundState.collectAsState()
    var title by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchRunningRound(roundId)
    }

    Scaffold(
        topBar = { AppBar(title = title) },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(MaterialTheme.spacing.medium)) {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
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
                is UiState.Success<RoundDetailsUiModel> -> {
                    title = state.data.roundDisplayName
                    RoundPlayingHomeMenu(
                        round = state.data,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun RoundPlayingHomeMenu(
    round: RoundDetailsUiModel,
    navController: NavController
) {
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        GradientButton(
            text = "Adicionar Partida",
            subtext = "Qual time ganhará dessa vez?",
            onClick = { navController.navigate(RoundPlayingRoutes.Match.route) }
        )

        TeamGroup(teams = round.teams)
        MatchList(matches = round.matches)
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

@PreviewLightDark
@Composable
private fun RoundPlayingHomeMenuPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            RoundPlayingHomeMenu(
                navController = rememberNavController(),
                round = RoundDetailsUiModel(
                    roundId = 1L,
                    roundDisplayName = "Rodada 1",
                    matches = listOf(),
                    teams = listOf(
                        TeamUiModel(
                            victories = 1,
                            teamDisplayName = "Time Azul",
                            teamDisplayDescription = "1 Vitória"
                        ),
                        TeamUiModel(
                            victories = 2,
                            teamDisplayName = "Time Preto",
                            teamDisplayDescription = "2 Vitória"
                        )
                    )
                )
            )
        }
    }
}
