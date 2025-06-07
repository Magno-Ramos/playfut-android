package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.domain.model.ui.TeamUiModel
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun TeamGroup(teams: List<TeamUiModel>) {
    val mTeams = teams.sortedByDescending { it.victories }
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
        Text(
            text = "Equipes",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (teams.size == 2) {
            TeamHorizontalSimple(mTeams.first(), mTeams.last())
        } else {
            TeamHorizontalScrollable(mTeams)
        }
    }
}

@Composable
private fun TeamHorizontalSimple(homeTeam: TeamUiModel, awayTeam: TeamUiModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        TeamItem(
            modifier = Modifier.weight(1f),
            team = homeTeam.teamDisplayName,
            description = homeTeam.teamDisplayDescription
        )
        TeamItem(
            modifier = Modifier.weight(1f),
            team = awayTeam.teamDisplayName,
            description = awayTeam.teamDisplayDescription
        )
    }
}

@Composable
private fun TeamHorizontalScrollable(teams: List<TeamUiModel>) {
    val horizontalScrollState = rememberScrollState()
    Row(
        modifier = Modifier.horizontalScroll(horizontalScrollState),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        teams.forEach {
            TeamItem(
                team = it.teamDisplayName,
                description = it.teamDisplayDescription
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TeamGroupPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            TeamGroup(
                teams = listOf(
                    TeamUiModel(
                        victories = 1,
                        teamDisplayName = "Time Azul",
                        teamDisplayDescription = "1 Vitória"
                    ),
                    TeamUiModel(
                        victories = 2,
                        teamDisplayName = "Time Preto",
                        teamDisplayDescription = "2 Vitórias"
                    )
                )
            )
        }
    }
}