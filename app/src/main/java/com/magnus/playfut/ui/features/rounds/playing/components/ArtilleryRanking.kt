package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.features.rounds.playing.states.RoundArtilleryItem
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun ArtilleryRanking(artillery: List<RoundArtilleryItem>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Artilheiros da Rodada (${artillery.size})",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
        ) {
            if (artillery.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
                        .padding(MaterialTheme.spacing.large),
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Nenhuma partida foi realizada ainda.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                ArtilleryHeader()
                HorizontalDivider()
            }

            artillery.sortedByDescending { it.goals }.forEachIndexed { index, it ->
                ArtilleryItem(position = index + 1, item = it)
                if (index < artillery.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun ArtilleryHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.tiny,
                vertical = MaterialTheme.spacing.small
            )
    ) {
        Text(
            modifier = Modifier.width(40.dp),
            text = "#",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Jogador",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.width(50.dp),
            text = "Gols",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ArtilleryItem(position: Int, item: RoundArtilleryItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.tiny,
                vertical = MaterialTheme.spacing.small
            )
    ) {
        Text(
            modifier = Modifier.width(40.dp),
            text = position.toString(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.weight(1f),
            text = item.name ?: "Desconhecido",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.width(50.dp),
            text = item.goals.toString(),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
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
                artillery = listOf(
                    RoundArtilleryItem("André", 2),
                    RoundArtilleryItem("Magno", 4),
                    RoundArtilleryItem("José", 2)
                )
            )
        }
    }
}