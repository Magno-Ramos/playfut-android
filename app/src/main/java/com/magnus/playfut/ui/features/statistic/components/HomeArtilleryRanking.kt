package com.magnus.playfut.ui.features.statistic.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun HomeArtilleryRanking(
    list: List<Artillery>,
    maxCount: Int? = null,
    onClickSeeAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        if (list.isNotEmpty()) {
            ContentRow(
                number = "#",
                player = "Player",
                goals = "Goals",
                isSecondary = true
            )
            HorizontalDivider()
        }

        list.take(maxCount ?: list.size).forEachIndexed { index, artillery ->
            ContentRow(
                number = (index + 1).toString(),
                player = artillery.playerName,
                goals = artillery.totalGoals.toString()
            )

            // add divider
            if (index < list.size - 1) {
                HorizontalDivider()
            }
        }

        if (maxCount != null && list.size > maxCount) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickSeeAll() }
                    .padding(MaterialTheme.spacing.extraSmall),
                text = "Ver Lista Completa",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun ContentRow(
    number: String,
    player: String,
    goals: String,
    isSecondary: Boolean = false
) {
    val contentColor = when (isSecondary) {
        true -> MaterialTheme.colorScheme.onSurfaceVariant
        false -> MaterialTheme.colorScheme.onSurface
    }

    Row(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)) {
        Text(
            modifier = Modifier.defaultMinSize(minWidth = 30.dp),
            text = number,
            color = contentColor,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.weight(1f),
            text = player,
            color = contentColor,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.defaultMinSize(minWidth = 50.dp),
            text = goals,
            color = contentColor,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
    }
}

@PreviewLightDark
@Composable
private fun HomeArtilleryRankingPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            HomeArtilleryRanking(
                list = listOf(
                    Artillery(playerName = "Player 1", totalGoals = 10),
                    Artillery(playerName = "Player 2", totalGoals = 20),
                    Artillery(playerName = "Player 3", totalGoals = 30),
                    Artillery(playerName = "Player 4", totalGoals = 40)
                ),
                maxCount = 8,
                onClickSeeAll = {}
            )
        }
    }
}