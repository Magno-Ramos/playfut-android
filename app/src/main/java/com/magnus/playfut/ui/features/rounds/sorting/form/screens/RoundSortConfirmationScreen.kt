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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.magnus.playfut.ui.domain.model.Team
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.TextInput
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundConfirmationHeader
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundTeam
import com.magnus.playfut.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundSortConfirmationScreen(
    viewModel: RoundSortViewModel,
    navController: NavController
) {
    val sheetState = rememberModalBottomSheetState()
    var showEditTeamBottomSheet by remember { mutableStateOf(false) }

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
                    onClick = { }
                ) {
                    Text(text = "Iniciar Rodada")
                }
            }
        }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            viewModel.teams?.takeIf { it.isNotEmpty() }?.let { mTeams ->
                RoundSortConfirmationContent(
                    teams = mTeams,
                    onClickEditTeam = { showEditTeamBottomSheet = true }
                )
            }
        }

        if (showEditTeamBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showEditTeamBottomSheet = false }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextInput(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onChangeValue = {},
                        label = "Editar nome do time",
                        requestFocus = true
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { showEditTeamBottomSheet = false }
                    ) {
                        Text("Salvar")
                    }
                }
            }
        }
    }
}

@Composable
private fun RoundSortConfirmationContent(
    modifier: Modifier = Modifier,
    teams: List<Team> = emptyList(),
    onClickEditTeam: (Team) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RoundConfirmationHeader(modifier = Modifier.padding(horizontal = 16.dp))
        for (team in teams) {
            RoundTeam(
                team = team,
                onClickEditTeam = onClickEditTeam,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun RoundSortConfirmationContentPreview() {
    AppTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            RoundSortConfirmationContent()
        }
    }
}