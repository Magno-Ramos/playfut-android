package com.magnus.playfut.ui.features.rounds.sorting.form.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.magnus.playfut.ui.domain.model.Team
import com.magnus.playfut.ui.domain.model.TeamSchema
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundConfirmationHeader
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundTeam
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun RoundSortConfirmationScreen(
    viewModel: RoundSortViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            AppToolbar(title = "Confirmação") {
                // navController.popBackStack()
            }
        }
    ) { paddings ->
        viewModel.teams?.let { mTeams ->
            RoundSortConfirmationContent(
                modifier = Modifier.padding(paddings),
                teams = mTeams
            )
        }
    }
}

@Composable
private fun RoundSortConfirmationContent(
    modifier: Modifier = Modifier,
    teams: List<Team> = emptyList()
) {
    val scrollState = rememberScrollState()
    Column (
        modifier = modifier.verticalScroll(scrollState).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RoundConfirmationHeader()
        teams.forEach { team ->
            RoundTeam(team = team)
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