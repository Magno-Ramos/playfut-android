package com.magnus.playfut.ui.features.rounds.playing.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.features.rounds.playing.components.MatchScore
import com.magnus.playfut.ui.features.rounds.playing.components.MatchScoreSize
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloseMatchConfirmActionSheet(
    homeTeamName: String,
    awayTeamName: String,
    homeScore: Int = 0,
    awayScore: Int = 0,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column {
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Encerrar Partida",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(MaterialTheme.spacing.small))
                Text(
                    text = "Tem certeza que deseja encerrar a partida entre $homeTeamName e $awayTeamName?",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Placar atual",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(MaterialTheme.spacing.tiny))
            MatchScore(
                homeTeam = homeTeamName,
                awayTeam = awayTeamName,
                homeScore = homeScore,
                awayScore = awayScore,
                matchScoreSize = MatchScoreSize.MEDIUM
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(text = "Cancelar")
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = { onConfirm() }
                ) {
                    Text(text = "Confirmar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun CloseMatchConfirmActionSheetPreview() {
    AppTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            CloseMatchConfirmActionSheet(
                homeTeamName = "Corinthians",
                awayTeamName = "São Paulo",
                onConfirm = {},
                onDismiss = {},
                sheetState = rememberStandardBottomSheetState(
                    initialValue = SheetValue.Expanded
                )
            )
        }
    }
}