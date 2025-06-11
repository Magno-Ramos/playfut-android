package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.features.rounds.playing.states.RoundScoreItem
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun ScoreList(
    scores: List<RoundScoreItem>,
    onClickRemove: (RoundScoreItem) -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
            .padding(MaterialTheme.spacing.medium)
    ) {
        Text(
            text = "Marcadores",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(MaterialTheme.spacing.small))

        if (scores.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Nenhum gol registrado ainda",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny)) {
            scores.forEach { score ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                        .padding(
                            start = MaterialTheme.spacing.extraSmall,
                            end = MaterialTheme.spacing.tiny,
                            top = MaterialTheme.spacing.micro,
                            bottom = MaterialTheme.spacing.micro
                        )
                ) {
                    Text(
                        text = "${score.playerName} - ${score.teamName}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(
                        onClick = { onClickRemove(score) }
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.RemoveCircleOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ScoreListPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            ScoreList(
                scores = listOf(
//                    RoundScoreItem(
//                        playerId = "1",
//                        teamId = "1",
//                        playerName = "Magno",
//                        teamName = "Brasil"
//                    ),
//                    RoundScoreItem(
//                        playerId = "2",
//                        teamId = "2",
//                        playerName = "Lucas",
//                        teamName = "Brasil"
//                    )
                )
            )
        }
    }
}