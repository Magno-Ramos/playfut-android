package com.magnus.playfut.ui.features.rounds.sorting.form.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.magnus.playfut.ui.domain.helper.DistributorTeamSchema
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingActivity
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortRoutes
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundConfirmationHeader
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundTeam
import com.magnus.playfut.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun RoundSortConfirmationScreen(
    viewModel: RoundSortViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val createRoundState by viewModel.createRoundState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    fun showError() {
        coroutineScope.launch {
            snackBarHostState.showSnackbar("Desculpe, ocorreu um erro!")
        }
    }

    when(val state = createRoundState) {
        ActionResultState.Idle -> {}
        ActionResultState.Loading -> {}
        is ActionResultState.Error -> {
            print(state.message)
            showError()
        }
        is ActionResultState.Success<Long> -> {
            val intent = RoundPlayingActivity.createIntent(context, state.data.toString())
            context.startActivity(intent)
            context.activity?.finish()
        }
    }

    fun onClickEditTeam(team: DistributorTeamSchema) {
        viewModel.editableTeam = team
        navController.navigate(RoundSortRoutes.EditTeam.route)
    }

    fun onClickStartRound() {
        coroutineScope.launch {
            viewModel.createRound()
        }
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Confirmação",
                onClickBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { onClickStartRound() }
                ) {
                    Text(text = "Iniciar Rodada")
                }
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            viewModel.distributorTeamSchema?.takeIf { it.isNotEmpty() }?.let { mTeams ->
                RoundSortConfirmationContent(
                    teams = mTeams,
                    onClickEditTeam = ::onClickEditTeam
                )
            }
        }
    }
}

@Composable
private fun RoundSortConfirmationContent(
    modifier: Modifier = Modifier,
    teams: List<DistributorTeamSchema> = emptyList(),
    onClickEditTeam: (DistributorTeamSchema) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        RoundConfirmationHeader()
        for (team in teams) {
            RoundTeam(
                team = team,
                onClickEditTeam = onClickEditTeam,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun RoundSortConfirmationContentPreview() {
    AppTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            RoundSortConfirmationContent(
                teams = listOf(
                    DistributorTeamSchema(
                        teamName = "Time 1",
                        goalKeepers = listOf(),
                        startPlaying = listOf(),
                        substitutes = listOf()
                    )
                )
            )
        }
    }
}