package com.magnus.playfut.ui.features.rounds.sorting.form.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.magnus.playfut.ui.domain.state.isError
import com.magnus.playfut.ui.domain.state.isLoading
import com.magnus.playfut.ui.domain.state.isSuccess
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortRoutes
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundSortingForm
import com.magnus.playfut.ui.features.rounds.sorting.form.state.ErrorState
import com.magnus.playfut.ui.features.rounds.sorting.form.state.LoadingState

@Composable
fun RoundSortFormScreen(
    groupId: String,
    viewModel: RoundSortViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val playersState by viewModel.playersState.collectAsState()
    val selectablePlayers by viewModel.selectablePlayers.collectAsState()

    val teamsCount = remember { mutableStateOf("2") }
    val playersCount = remember { mutableStateOf("7") }

    LaunchedEffect(Unit) {
        viewModel.fetchPlayers(groupId)
    }

    fun openSelectionScreen() {
        navController.navigate(RoundSortRoutes.PlayerSelection.route)
    }

    Scaffold(
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
                        onClick = {}
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
                    teamsCount = teamsCount.value,
                    playersCount = playersCount.value,
                    onChangeTeamsCount = { teamsCount.value = it },
                    onChangePlayersCount = { playersCount.value = it },
                    onClickChangePlayers = { openSelectionScreen() }
                )
            }
        }
    }
}