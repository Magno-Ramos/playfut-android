package com.magnus.playfut.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun ProVersion(
    title: String,
    action: String,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(MaterialTheme.spacing.medium)
    ) {
        Icon(
            imageVector = Icons.Rounded.Diamond,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = action,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
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
            ProVersion(
                title = "Liberado na Versão PRO",
                action = "Desbloquear"
            )
        }
    }
}