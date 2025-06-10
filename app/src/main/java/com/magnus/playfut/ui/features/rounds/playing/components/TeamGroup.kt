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
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun TeamGroup(
    teams: List<RoundTeamItem>,
    onClickItem: (RoundTeamItem) -> Unit = {}
) {
    val mTeams = teams.sortedByDescending { it.victories }
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {
        Text(
            text = "Equipes",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (teams.size == 2) {
            TeamHorizontalSimple(
                homeTeam = mTeams.first(),
                awayTeam = mTeams.last(),
                onClickItem = onClickItem
            )
        } else {
            TeamHorizontalScrollable(
                teams = mTeams,
                onClickItem = onClickItem
            )
        }
    }
}

@Composable
private fun TeamHorizontalSimple(
    homeTeam: RoundTeamItem,
    awayTeam: RoundTeamItem,
    onClickItem: (RoundTeamItem) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        TeamItem(
            modifier = Modifier.weight(1f),
            team = homeTeam.name,
            description = getTeamDescription(homeTeam.victories),
            onClickItem = { onClickItem(homeTeam) }
        )
        TeamItem(
            modifier = Modifier.weight(1f),
            team = awayTeam.name,
            description = getTeamDescription(awayTeam.victories),
            onClickItem = { onClickItem(awayTeam) }
        )
    }
}

@Composable
private fun TeamHorizontalScrollable(
    teams: List<RoundTeamItem>,
    onClickItem: (RoundTeamItem) -> Unit = {}
) {
    val horizontalScrollState = rememberScrollState()
    Row(
        modifier = Modifier.horizontalScroll(horizontalScrollState),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        teams.forEach {
            TeamItem(
                team = it.name,
                description = getTeamDescription(it.victories),
                onClickItem = { onClickItem(it) }
            )
        }
    }
}

private fun getTeamDescription(victories: Int): String {
    return when (victories) {
        1 -> "$victories Vitória"
        else -> "$victories Vitórias"
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
                    RoundTeamItem(
                        id = "1",
                        name = "Time A",
                        victories = 1
                    ),
                    RoundTeamItem(
                        id = "2",
                        name = "Time B",
                        victories = 2
                    )
                )
            )
        }
    }
}