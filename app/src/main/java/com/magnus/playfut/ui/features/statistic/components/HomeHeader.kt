package com.magnus.playfut.ui.features.statistic.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun HomeHeader(
    totalRounds: Int,
    totalMatches: Int,
    totalGoals: Int
) {
    Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)) {

        // it saves case where, each round is played only once
        if (totalMatches > totalRounds) {
            Card(
                modifier = Modifier.weight(1f),
                title = "Rodadas",
                value = totalRounds.toString()
            )
        }

        Card(
            modifier = Modifier.weight(1f),
            title = "Partidas",
            value = totalMatches.toString()
        )
        Card(
            modifier = Modifier.weight(1f),
            title = "Gols",
            value = totalGoals.toString()
        )
    }
}

@Composable
private fun Card(
    modifier: Modifier,
    title: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.extraSmall
            )
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@PreviewLightDark
@Composable
private fun HomeHeaderPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            HomeHeader(
                totalRounds = 5,
                totalMatches = 10,
                totalGoals = 13
            )
        }
    }
}