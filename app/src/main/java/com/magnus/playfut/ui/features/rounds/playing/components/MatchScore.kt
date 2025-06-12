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
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

enum class MatchScoreSize {
    MEDIUM,
    LARGE
}

@Composable
fun MatchScore(
    homeTeam: String = "Time Azul",
    awayTeam: String = "Time Vermelho",
    homeScore: Int = 0,
    awayScore: Int = 0,
    matchScoreSize: MatchScoreSize = MatchScoreSize.LARGE
) {
    val teamFontSize = when (matchScoreSize) {
        MatchScoreSize.MEDIUM -> 14.sp
        MatchScoreSize.LARGE -> 16.sp
    }

    val scoreFontSize = when (matchScoreSize) {
        MatchScoreSize.MEDIUM -> 28.sp
        MatchScoreSize.LARGE -> 36.sp
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(MaterialTheme.spacing.medium)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = homeTeam,
                fontSize = teamFontSize,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = homeScore.toString(),
                fontSize = scoreFontSize,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = "vs",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = awayTeam,
                fontSize = teamFontSize,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = awayScore.toString(),
                fontSize = scoreFontSize,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun MatchSelectorPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            MatchScore()
            MatchScore(matchScoreSize = MatchScoreSize.MEDIUM)
        }
    }
}