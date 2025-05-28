package com.magnus.playfut.ui.features.player.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun PlayerGroup(
    type: PlayerType,
    group: List<Player>
) {
    Column {
        Text(modifier = Modifier.padding(start = 8.dp), text = type.type, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(12.dp))
        group.forEach { player ->
            PlayerItem(player = player)
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
private fun PlayerGroupPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(AppColor.bgPrimary)
                .padding(16.dp)
        ) {
            PlayerGroup(
                type = PlayerType.MIDFIELDER,
                group = listOf(
                    Player(
                        name = "Magno",
                        quality = 4
                    ),
                    Player(
                        name = "Lucas",
                        quality = 3
                    )
                )
            )
        }
    }
}