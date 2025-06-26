package com.magnus.playfut.ui.features.rounds.sorting.form.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
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
    Column(
        modifier = modifier.padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
    ) {
        Column {
            Text(
                text = "Modo de distribuição",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(Modifier.height(MaterialTheme.spacing.small))
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                DistributionType.entries.forEach { type ->
                    RoundFilterChip(
                        modifier = Modifier.weight(1f),
                        label = type.title,
                        leadingIcon = type.icon,
                        selected = type == distributionType,
                        onClickSelect = { onChangeDistributionType(type) }
                    )
                }
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
            label = "Jogadores por time? (Com goleiro)",
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