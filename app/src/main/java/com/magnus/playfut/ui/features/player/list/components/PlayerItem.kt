package com.magnus.playfut.ui.features.player.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.magnus.playfut.R
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun PlayerItem(
    player: Player,
    onClick: () -> Unit = {}
) {
    val typeIcon = when (player.type) {
        PlayerType.GOALKEEPER -> rememberVectorPainter(Icons.Default.SportsMma)
        else -> painterResource(R.drawable.ball_soccer)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(90.dp))
            .clickable(
                onClick = onClick,
                role = Role.Button,
            )
            .background(AppColor.bgSecondary)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = typeIcon,
            contentDescription = null,
            tint = AppColor.tertiaryText
        )
        Text(
            text = player.name,
            modifier = Modifier.weight(1f),
            color = AppColor.primaryText
        )
        Text(
            text = player.skillLevel.toString(),
            color = AppColor.primaryText
        )
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Rounded.Star,
            contentDescription = null,
            tint = AppColor.yellow
        )
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = AppColor.secondaryText
        )
    }
}

@Preview
@Composable
private fun PlayerItemPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppColor.bgPrimary)
                .padding(16.dp)
        ) {
            PlayerItem(
                player = Player(
                    name = "Magno",
                    skillLevel = 4,
                    type = PlayerType.FORWARD
                )
            )
        }
    }
}