package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun Stopwatch(
    timeInSeconds: Long = 0L,
    isRunning: Boolean,
    onReset: () -> Unit = {},
    onToggle: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
            .padding(MaterialTheme.spacing.medium)
    ) {
        StopwatchHeader(timeInSeconds)
        StopwatchActions(
            isRunning = isRunning,
            timeInSeconds = timeInSeconds,
            onReset = onReset,
            onToggle = onToggle
        )
    }
}

@Composable
private fun StopwatchHeader(timeInSeconds: Long) {
    val minutes = (timeInSeconds / 60).toString().padStart(2, '0')
    val seconds = (timeInSeconds % 60).toString().padStart(2, '0')
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Cronômetro",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "$minutes:$seconds",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun StopwatchActions(
    isRunning: Boolean,
    timeInSeconds: Long,
    onReset: () -> Unit,
    onToggle: () -> Unit
) {

    val actionText = when {
        isRunning -> "Pausar"
        timeInSeconds > 0 -> "Continuar"
        else -> "Iniciar"
    }

    val buttonColor = when {
        isRunning -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.primary
    }

    val buttonTextColor = when {
        isRunning -> MaterialTheme.colorScheme.onError
        else -> MaterialTheme.colorScheme.onPrimary
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            onClick = onReset
        ) {
            Text(
                text = "Zerar",
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = buttonTextColor
            ),
            onClick = onToggle
        ) {
            Text(
                text = actionText,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun StopwatchPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            Stopwatch(
                timeInSeconds = 92L,
                isRunning = false
            )
        }
    }
}

