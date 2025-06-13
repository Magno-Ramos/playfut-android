package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.R
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun WinnerCard(
    groupName: String,
    teamName: String,
    wins: Int,
    draws: Int,
    losses: Int,
    goalsScored: Int,
    goalsConceded: Int,
    date: Date
) {

    val date = DateTimeFormatter
        .ofPattern("dd/MM/yyyy")
        .format(date.toInstant().atZone(ZoneId.systemDefault()))

    val winnerText = when (wins) {
        1 -> "$wins Vitória"
        else -> "$wins Vitórias"
    }

    val drawText = when (draws) {
        1 -> "$draws Empate"
        else -> "$draws Empates"
    }

    val lossText = when (losses) {
        1 -> "$losses Derrota"
        else -> "$losses Derrotas"
    }

    val goalsScoredText = when (goalsScored) {
        1 -> "$goalsScored Gol Marcado"
        else -> "$goalsScored Gols Marcados"
    }

    val goalsConcededText = when (goalsConceded) {
        1 -> "$goalsConceded Gol Sofrido"
        else -> "$goalsConceded Gols Sofridos"
    }

    val backgroundDarkTransparent = Color.Black.copy(alpha = 0.15f)
    val cornerShape = RoundedCornerShape(8.dp)

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(brush = Brush.linearGradient(colors = AppColor.gradientMagic))
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.trophy),
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = "Vencedor Final",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$groupName • $date",
                color = Color.White,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundDarkTransparent, cornerShape)
                    .padding(MaterialTheme.spacing.medium)
            ) {

                Text(
                    text = teamName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$winnerText • $drawText • $lossText",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundDarkTransparent, cornerShape)
                    .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small),
                text = goalsScoredText,
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(4.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundDarkTransparent, cornerShape)
                    .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small),
                text = goalsConcededText,
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WinnerCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            WinnerCard(
                groupName = "PodFut",
                teamName = "Corinthians",
                wins = 4,
                draws = 0,
                losses = 1,
                goalsScored = 10,
                goalsConceded = 5,
                date = Date()
            )
        }
    }
}