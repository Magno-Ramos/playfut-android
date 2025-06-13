package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.magnus.playfut.domain.state.UiState
import com.magnus.playfut.domain.state.asSuccess
import com.magnus.playfut.domain.state.isSuccess
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppAlertDialog
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingRoutes
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.ArtilleryRanking
import com.magnus.playfut.ui.features.rounds.playing.components.MatchList
import com.magnus.playfut.ui.features.rounds.playing.components.TeamGroup
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayingHomeViewState
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import com.magnus.playfut.ui.theme.spacing

@Composable
fun RoundPlayingHomeScreen(
    roundId: String,
    viewModel: RoundPlayingViewModel,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }

    val context = LocalContext.current
    val roundState by viewModel.roundState.collectAsState()
    val closeRoundState by viewModel.closeRoundState.collectAsState()

    if (roundState.isSuccess()) {
        title = roundState.asSuccess()?.data?.roundName ?: ""
    }

    if (closeRoundState.isSuccess()) {
        navController.navigate(RoundPlayingRoutes.RoundResult.route) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun onClickConfirmCloseRound() {
        roundState.asSuccess()?.data?.let {
            viewModel.closeRound()
        }
    }

    fun onClickTeam(team: RoundTeamItem) {
        navController.navigate(RoundPlayingRoutes.TeamDetail.createRoute(team.id, roundId))
    }

    LaunchedEffect(Unit) {
        viewModel.fetchRound(roundId)
    }

    Scaffold(
        topBar = { AppToolbar(title = title, onClickBack = { context.activity?.finish() }) },
        bottomBar = {
            BottomAppBar (contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium)) {
                Button(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    onClick = { navController.navigate(RoundPlayingRoutes.MatchSelection.route) }
                ) {
                    Text(text = "Nova Partida")
                }
            }
        }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            when (val state = roundState) {
                UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView("Desculpe, ocorreu um erro!")
                is UiState.Success<RoundPlayingHomeViewState> -> RoundPlayingHomeContent(
                    state = state.data,
                    onClickConfirmCloseRound = ::onClickConfirmCloseRound,
                    onClickTeam = ::onClickTeam
                )
            }
        }
    }
}

@Composable
private fun RoundPlayingHomeContent(
    state: RoundPlayingHomeViewState,
    onClickConfirmCloseRound: () -> Unit = {},
    onClickTeam: (RoundTeamItem) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var closeRoundDialogVisible by remember { mutableStateOf(false) }

    fun onClickCloseRound() {
        closeRoundDialogVisible = true
    }

    Column(Modifier.verticalScroll(scrollState)) {
        RoundPlayingHomeMenu(
            viewState = state,
            onClickTeam = { onClickTeam(it) }
        )

        TextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { onClickCloseRound() }
        ) {
            Text(
                text = "Finalizar Rodada",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(MaterialTheme.spacing.medium))

        if (closeRoundDialogVisible) {
            AppAlertDialog(
                dialogTitle = "Finalizar Rodada",
                dialogText = "Tem certeza que deseja finalizar essa rodada?",
                confirmButtonText = "Confirmar",
                dismissButtonText = "Cancelar",
                onDismissRequest = { closeRoundDialogVisible = false },
                onConfirmation = { onClickConfirmCloseRound() }
            )
        }
    }
}

@Composable
private fun RoundPlayingHomeMenu(
    onClickTeam: (RoundTeamItem) -> Unit = {},
    viewState: RoundPlayingHomeViewState
) {
    Column(
        modifier = Modifier.padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        TeamGroup(
            teams = viewState.teams,
            onClickItem = { team -> onClickTeam(team) }
        )
        ArtilleryRanking(artillery = viewState.artillery)
        MatchList(matches = viewState.matches)
    }
}
