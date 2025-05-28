package com.magnus.playfut.ui.features.home.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.Sports
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.magnus.playfut.R
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.Round
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun GroupItem(
    group: Group,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    GradientBorderBox(
        cornerRadius = 8.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                enabled = true,
                onClick = { onClick() },
                role = Role.Button
            )
            .background(AppColor.bgSecondary)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.Handshake,
                    contentDescription = null,
                    tint = AppColor.primaryText
                )
                Text(text = group.name, fontWeight = FontWeight.SemiBold, color = AppColor.primaryText)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.Groups,
                    contentDescription = null,
                    tint = AppColor.primaryText
                )
                Text(
                    text = group.players.toPlayersCountString(context),
                    fontWeight = FontWeight.Normal,
                    color = AppColor.primaryText
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.Sports,
                    contentDescription = null,
                    tint = AppColor.primaryText
                )
                Text(
                    text = group.rounds.toRealizedRoundsString(context),
                    fontWeight = FontWeight.Normal,
                    color = AppColor.primaryText
                )
            }
        }
    }
}

@Composable
fun GradientBorderBox(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    borderWidth: Dp = 1.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(colors = listOf(AppColor.primary, AppColor.green)),
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(borderWidth)
            .background(
                color = AppColor.white,
                shape = RoundedCornerShape(cornerRadius - borderWidth)
            )
    ) { content() }
}

fun List<Round>.toRealizedRoundsString(context: Context): String {
    return if (isEmpty()) {
        context.getString(R.string.no_rounds_realized)
    } else {
        context.resources.getQuantityString(R.plurals.rounds_realized, size, size)
    }
}

fun List<Player>.toPlayersCountString(context: Context): String {
    return if (isEmpty()) {
        context.getString(R.string.no_players)
    } else {
        context.resources.getQuantityString(R.plurals.players_count, size, size)
    }
}

@Preview
@Composable
private fun GroupItemPreview() {
    AppTheme {
        Column(
            Modifier
                .background(AppColor.bgPrimary)
                .padding(16.dp)
        ) {
            GroupItem(
                group = Group(
                    id = "1",
                    name = "Grupo 1",
                    players = listOf(),
                    rounds = listOf()
                )
            )
        }
    }
}