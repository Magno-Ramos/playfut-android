package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.domain.model.ui.MatchUiModel
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing


@Composable
fun MatchList(matches: List<MatchUiModel>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Partidas realizadas (${matches.size})",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (matches.isEmpty()) {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                    .padding(MaterialTheme.spacing.large),
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Nenhuma partida foi realizada ainda.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        for (match in matches) {
            MatchItem(
                homeTeam = match.homeTeamName,
                awayTeam = match.awayTeamName,
                homeScore = match.homeTeamScore,
                awayScore = match.awayTeamScore
            )
        }
    }
}

@Composable
fun MatchItem(
    homeTeam: String,
    awayTeam: String,
    homeScore: Int,
    awayScore: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                MaterialTheme.spacing.medium,
                MaterialTheme.spacing.extraSmall
            )
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = homeTeam,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = awayTeam,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Column {
            Text(
                text = homeScore.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = awayScore.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        VerticalDivider(modifier = Modifier.height(14.dp), thickness = 1.dp)

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@PreviewLightDark
@Composable
private fun MatchItemPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            MatchList(
                matches = listOf(
                    MatchUiModel(
                        matchId = 1,
                        homeTeamName = "Time A",
                        awayTeamName = "Time B",
                        homeTeamScore = 2,
                        awayTeamScore = 1,
                        scores = emptyList()
                    ),
                    MatchUiModel(
                        matchId = 1,
                        homeTeamName = "Time A",
                        awayTeamName = "Time B",
                        homeTeamScore = 3,
                        awayTeamScore = 3,
                        scores = emptyList()
                    )
                )
            )
        }
    }
}