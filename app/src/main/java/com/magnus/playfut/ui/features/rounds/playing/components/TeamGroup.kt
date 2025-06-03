package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.domain.model.Team
import com.magnus.playfut.ui.domain.model.TeamSchema
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun TeamGroup(teams: List<Team>) {
    Column (verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
        Text(
            text = "Equipes",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        teams.forEach {
            TeamItem(team = it)
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
                    Team(
                        id = "1",
                        name = "Time A",
                        schema = TeamSchema(
                            goalKeepers = listOf(),
                            startPlaying = listOf(),
                            substitutes = listOf(),
                            replacementSuggestions = listOf()
                        )
                    ),
                    Team(
                        id = "1",
                        name = "Time B",
                        schema = TeamSchema(
                            goalKeepers = listOf(),
                            startPlaying = listOf(),
                            substitutes = listOf(),
                            replacementSuggestions = listOf()
                        )
                    )
                )
            )
        }
    }
}