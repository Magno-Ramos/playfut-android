package com.magnus.playfut.ui.features.rounds.sorting.form.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.magnus.playfut.domain.helper.DistributionType
import com.magnus.playfut.ui.features.common.TextInput
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun RoundSortingForm(
    modifier: Modifier = Modifier,
    teamsCount: String,
    playersCount: String,
    totalPlayers: String,
    distributionType: DistributionType,
    onChangeDistributionType: (DistributionType) -> Unit = {},
    onChangeTeamsCount: (String) -> Unit = {},
    onChangePlayersCount: (String) -> Unit = {},
    onClickChangePlayers: () -> Unit = {},
) {
    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            DistributionType.entries.forEach { type ->
                FilterChip(
                    label = { Text(type.title) },
                    onClick = { onChangeDistributionType(type) },
                    selected = distributionType == type,
                    leadingIcon = {
                        Icon(
                            imageVector = type.icon,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                )
            }
        }

        RoundPlayersInput(
            totalPlayers = totalPlayers,
            onClickChange = { onClickChangePlayers() }
        )
        TextInput(
            label = "Quantos times?",
            value = teamsCount,
            keyboardType = KeyboardType.Number,
            onChangeValue = { value ->
                if (value.all { it.isDigit() }) {
                    onChangeTeamsCount(value)
                }
            }
        )
        TextInput(
            label = "Quantos Jogadores por time?",
            value = playersCount,
            keyboardType = KeyboardType.Number,
            onChangeValue = { value ->
                if (value.all { it.isDigit() }) {
                    onChangePlayersCount(value)
                }
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun RoundSortingFormPreview() {
    AppTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            RoundSortingForm(
                totalPlayers = "1",
                teamsCount = "2",
                playersCount = "5",
                distributionType = DistributionType.RANDOM
            )
        }
    }
}