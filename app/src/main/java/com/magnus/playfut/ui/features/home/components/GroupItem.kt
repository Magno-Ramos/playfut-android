package com.magnus.playfut.ui.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.magnus.playfut.R
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun GroupItem(group: Group) {
    Column(
        modifier = Modifier
            .background(AppColor.bgSecondary, RoundedCornerShape(8.dp))
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
                text = group.players.size.toPlayersCountString(),
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
                text = group.rounds.size.toRealizedRoundsString(),
                fontWeight = FontWeight.Normal,
                color = AppColor.primaryText
            )
        }
    }
}

@Composable
private fun Int.toRealizedRoundsString(): String = LocalContext.current.resources.getQuantityString(
    R.plurals.rounds_realized, this, this
)

@Composable
private fun Int.toPlayersCountString(): String = LocalContext.current.resources.getQuantityString(
    R.plurals.players_count, this, this
)

@Preview
@Composable
private fun GroupItemPreview() {
    AppTheme {
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