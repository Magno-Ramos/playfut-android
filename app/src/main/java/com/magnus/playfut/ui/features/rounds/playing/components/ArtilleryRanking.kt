package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.R
import com.magnus.playfut.ui.domain.model.ui.Artillery
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun ArtilleryRanking(ranking: List<Artillery>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Artilheiros da Rodada (${ranking.size})",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
        ) {
            if (ranking.isEmpty()) {
                Box(
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

            ranking.sortedByDescending { it.totalGoals }.forEachIndexed { index, it ->
                ArtilleryItem(item = it)
                if (index < ranking.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun ArtilleryItem(item: Artillery) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium, MaterialTheme.spacing.extraSmall)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = item.playerName,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
        Text(
            text = item.totalGoals.toString(),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            painter = painterResource(R.drawable.soccer_ball_net),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@PreviewLightDark
@Composable
private fun ArtilleryRankingPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            ArtilleryRanking(
                ranking = listOf(

                )
            )
        }
    }
}