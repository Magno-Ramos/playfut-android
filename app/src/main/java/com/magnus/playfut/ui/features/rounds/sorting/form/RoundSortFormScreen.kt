package com.magnus.playfut.ui.features.rounds.sorting.form

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.extensions.getParcelableArrayListExtraCompat
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortPlayerSelectionActivity.Companion.EXTRA_PLAYERS_LIST
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundSortingForm
import com.magnus.playfut.ui.features.rounds.sorting.form.model.SelectablePlayer
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoundSortFormScreen(
    viewModel: RoundSortFormViewModel = koinViewModel(),
    groupId: String
) {
    val context = LocalContext.current
    val teamsCount = remember { mutableStateOf("2") }
    val playersCount = remember { mutableStateOf("7") }
    val players = viewModel.selectablePlayers.collectAsState()
    val playerState = viewModel.playersState.collectAsState()

    fun handlePlayerSelectionResult(data: Intent?) {
        val selectablePlayers: ArrayList<SelectablePlayer>? =
            data?.getParcelableArrayListExtraCompat(EXTRA_PLAYERS_LIST)

        if (selectablePlayers != null) {
            viewModel.setPlayers(selectablePlayers)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            handlePlayerSelectionResult(data)
        }
    }

    fun openSelectionScreen() {
        val intent = RoundSortPlayerSelectionActivity.createIntent(context, players.value)
        launcher.launch(intent)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchPlayers(groupId)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Sortear Times",
                onClickBack = { context.activity?.finish() }
            )
        },
        bottomBar = {
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
    ) { paddings ->
        Box(Modifier.padding(paddings)) {
            when (playerState.value) {
                UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView(message = "Desculpe, ocorreu um erro")
                is UiState.Success<*> -> {
                    RoundSortingForm(
                        totalPlayers = players.value.filter { it.selected }.size.toString(),
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
}