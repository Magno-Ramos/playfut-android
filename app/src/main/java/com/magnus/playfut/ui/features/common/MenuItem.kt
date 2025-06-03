package com.magnus.playfut.ui.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    isPrimary: Boolean = false,
    onClick: () -> Unit = {}
) {
    val primaryTextColor = if (isPrimary) MaterialTheme.colorScheme.onPrimary
    else MaterialTheme.colorScheme.onBackground

    val secondaryTextColor = if (isPrimary) MaterialTheme.colorScheme.onSecondary
    else MaterialTheme.colorScheme.onSurface

    var modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .clickable(enabled = true, onClick = onClick)

    modifier = if (isPrimary) {
        modifier.background(MaterialTheme.colorScheme.primary)
    } else {
        modifier.background(MaterialTheme.colorScheme.surface)
    }

    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = secondaryTextColor
        )

        if (subtitle.isNullOrBlank()) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium,
                color = primaryTextColor,
                fontSize = 14.sp
            )
        } else {
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    color = primaryTextColor,
                    fontSize = 14.sp
                )
                Text(
                    text = subtitle,
                    color = secondaryTextColor,
                    fontSize = TextUnit(value = 12f, type = TextUnitType.Sp),
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = secondaryTextColor
        )
    }
}

@PreviewLightDark
@Composable
private fun MenuItemPreview() {
    AppTheme {
        Column (verticalArrangement = Arrangement.spacedBy(16.dp)) {
            MenuItem(
                icon = Icons.Default.Sports,
                title = "Nova Rodada",
                subtitle = "Faça o sorteio dos times para nova rodada",
                isPrimary = true
            )
            MenuItem(
                icon = Icons.Default.Sports,
                title = "Nova Rodada",
                subtitle = "Faça o sorteio dos times para nova rodada",
                isPrimary = false
            )
        }
    }
}