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
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun TeamSelector() {
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
            InputSelect(
                modifier = Modifier.weight(1f),
                options = listOf("Time A", "Time B", "Time C"),
                selectedOption = "Time A",
                onOptionSelected = { }
            )
            InputSelect(
                modifier = Modifier.weight(1f),
                options = listOf("Time D", "Time E", "Time F"),
                selectedOption = "Time D",
                onOptionSelected = { }
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
            TeamSelector()
        }
    }
}