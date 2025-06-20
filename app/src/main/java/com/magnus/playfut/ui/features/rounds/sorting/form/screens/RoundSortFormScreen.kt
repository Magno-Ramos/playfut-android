package com.magnus.playfut.ui.features.rounds.sorting.form.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.magnus.playfut.domain.helper.DistributionType
import com.magnus.playfut.domain.helper.PlayerDistributorV3
import com.magnus.playfut.domain.state.isError
import com.magnus.playfut.domain.state.isLoading
import com.magnus.playfut.domain.state.isSuccess
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortRoutes
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundSortingForm
import com.magnus.playfut.ui.features.rounds.sorting.form.model.toPlayer
import com.magnus.playfut.ui.features.rounds.sorting.form.state.ErrorState
import com.magnus.playfut.ui.features.rounds.sorting.form.state.LoadingState
import com.magnus.playfut.ui.theme.AppColor
import kotlinx.coroutines.launch

@Composable
fun RoundSortFormScreen(
    groupId: String,
    viewModel: RoundSortViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val playersState by viewModel.playersState.collectAsStateWithLifecycle()
    val selectablePlayers by viewModel.selectablePlayers.collectAsStateWithLifecycle()
    val teamsCount by viewModel.teamsCount.collectAsStateWithLifecycle(initialValue = 2)
    val playersCount by viewModel.playersCount.collectAsStateWithLifecycle(initialValue = 5)
    val playerDistributionType by viewModel.distributionType.collectAsStateWithLifecycle(DistributionType.RANDOM)

    LaunchedEffect(Unit) {
        viewModel.fetchPlayers(groupId)
    }

    fun showErrorSnack(message: String) = coroutineScope.launch {
        snackBarHostState.showSnackbar(message = message)
    }

    fun openSelectionScreen() {
        navController.navigate(RoundSortRoutes.PlayerSelection.route)
    }

    fun sortPlayersInTeams() {
        runCatching {
            val selectedPlayers = selectablePlayers.filter { it.selected }.map { it.toPlayer() }
            viewModel.teamSchema = PlayerDistributorV3.distribute(
                players = selectedPlayers,
                teamCount = teamsCount ?: 0,
                startersPerTeam = playersCount ?: 0,
                distributionType = playerDistributionType
            )
        }.onSuccess {
            navController.navigate(RoundSortRoutes.FormConfirmation.route)
        }.onFailure {
            showErrorSnack(it.message ?: "Desculpe, ocorreu um erro!")
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = AppColor.red,
                    contentColor = AppColor.white
                )
            }
        },
        topBar = {
            AppToolbar(
                title = "Sortear Times",
                onClickBack = { context.activity?.finish() }
            )
        },
        bottomBar = {
            if (playersState.isSuccess()) {
                BottomAppBar(contentPadding = PaddingValues(16.dp)) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        onClick = { sortPlayersInTeams() }
                    ) {
                        Text(text = "Sortear")
                    }
                }
            }
        }
    ) { paddings ->
        Box(Modifier.padding(paddings)) {
            when {
                playersState.isLoading() -> LoadingState()
                playersState.isError() -> ErrorState()
                playersState.isSuccess() -> RoundSortingForm(
                    totalPlayers = selectablePlayers.filter { it.selected }.size.toString(),
                    teamsCount = teamsCount?.toString() ?: "",
                    playersCount = playersCount?.toString() ?: "",
                    distributionType = playerDistributionType,
                    onChangeDistributionType = { viewModel.updateDistributionType(it) },
                    onChangeTeamsCount = { viewModel.updateTeamsCount(it.toIntOrNull()) },
                    onChangePlayersCount = { viewModel.updatePlayersCount(it.toIntOrNull()) },
                    onClickChangePlayers = { openSelectionScreen() }
                )
            }
        }
    }
}