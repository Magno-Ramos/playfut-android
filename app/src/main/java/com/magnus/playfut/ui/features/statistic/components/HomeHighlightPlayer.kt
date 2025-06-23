package com.magnus.playfut.ui.features.statistic.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.magnus.playfut.R
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun HomeHighlightPlayer(
    icon: Painter,
    player: String,
    highlight: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(MaterialTheme.spacing.medium)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
        Text(
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(player)
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                    append(", ")
                    append(highlight)
                }
            }
        )
        Spacer(Modifier.height(MaterialTheme.spacing.tiny))
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@PreviewLightDark
@Composable
private fun HomeHighlightPlayerPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            HomeHighlightPlayer(
                icon = painterResource(R.drawable.crown),
                player = "Magno",
                highlight = "Medalhão da sorte",
                description = "Venceu 19 das 23 partidas que disputou!",
            )
        }
    }
}