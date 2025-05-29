package com.magnus.playfut.ui.features.rounds.sorting.form.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.features.common.TextInput
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun RoundSortingForm(
    modifier: Modifier = Modifier,
    teamsCount: String,
    playersCount: String,
    totalPlayers: String,
    onChangeTeamsCount: (String) -> Unit = {},
    onChangePlayersCount: (String) -> Unit = {},
    onClickChangePlayers: () -> Unit = {},
) {
    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
        Column (Modifier.background(MaterialTheme.colorScheme.background)) {
            RoundSortingForm(
                totalPlayers = "1",
                teamsCount = "2",
                playersCount = "5"
            )
        }
    }
}