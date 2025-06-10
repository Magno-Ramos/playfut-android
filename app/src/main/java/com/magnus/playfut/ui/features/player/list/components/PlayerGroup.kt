package com.magnus.playfut.ui.features.player.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.domain.model.structure.Player
import com.magnus.playfut.ui.domain.model.structure.PlayerType
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun PlayerGroup(
    type: PlayerType,
    group: List<Player>,
    onClickPlayer: (Player) -> Unit = {}
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = type.type,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        group.forEach { player ->
            PlayerItem(player = player, onClick = { onClickPlayer(player) })
            Spacer(Modifier.height(4.dp))
        }
    }
}

@PreviewLightDark
@Composable
private fun PlayerGroupPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            PlayerGroup(
                type = PlayerType.MIDFIELDER,
                group = listOf(
                    Player(
                        name = "Magno",
                        skillLevel = 4
                    ),
                    Player(
                        name = "Lucas",
                        skillLevel = 3
                    )
                )
            )
        }
    }
}