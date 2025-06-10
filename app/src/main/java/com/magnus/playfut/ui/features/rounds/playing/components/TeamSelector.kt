package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.features.common.InputSelect
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun TeamSelector(
    homeTeam: RoundTeamItem,
    awayTeam: RoundTeamItem,
    homeOptions: List<Pair<String, String>>,
    awayOptions: List<Pair<String, String>>,
    onSelectHomeTeam: (String) -> Unit,
    onSelectAwayTeam: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Confronto",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = Modifier.fillMaxWidth()
        ) {
            InputSelect<String>(
                modifier = Modifier.weight(1f),
                options = homeOptions,
                selectedOption = homeTeam.name,
                onOptionSelected = onSelectHomeTeam
            )
            InputSelect<String>(
                modifier = Modifier.weight(1f),
                options = awayOptions,
                selectedOption = awayTeam.name,
                onOptionSelected = onSelectAwayTeam
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TeamSelectorPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            TeamSelector(
                homeTeam = RoundTeamItem(
                    id = "1",
                    name = "Time Azul",
                    victories = 1
                ),
                awayTeam = RoundTeamItem(
                    id = "2",
                    name = "Time Preto",
                    victories = 2
                ),
                homeOptions = listOf("Time A" to "1", "Time B" to "2"),
                awayOptions = listOf("Time C" to "3", "Time D" to "4"),
                onSelectHomeTeam = {},
                onSelectAwayTeam = {}
            )
        }
    }
}