package com.magnus.playfut.ui.features.rounds.sorting.form.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.R
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.Team
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun RoundTeam(
    modifier: Modifier = Modifier,
    team: Team,
    onClickEditTeam: (Team) -> Unit = {}
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .clickable { onClickEditTeam(team) }
                .padding(16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.apparel_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.weight(1f),
                text = team.name,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
            Text(
                text = "Editar",
                fontWeight = FontWeight.Bold,
                color = AppColor.blue,
                fontSize = 14.sp
            )
        }

        HorizontalDivider()
        TeamPlayerSchema(
            title = "Goleiros",
            players = team.schema.goalKeepers,
            opened = false
        )

        HorizontalDivider()
        TeamPlayerSchema(
            title = "Titulares",
            players = team.schema.startPlaying,
            opened = false
        )

        HorizontalDivider()
        TeamPlayerSchema(
            title = "Reservas",
            players = team.schema.substitutes,
            opened = false
        )
    }
}

@Composable
fun TeamPlayerSchema(
    modifier: Modifier = Modifier,
    title: String,
    players: List<Player>,
    opened: Boolean = false
) {
    var contentOpen by remember { mutableStateOf(opened) }

    Row(
        modifier = modifier
            .clickable { contentOpen = !contentOpen }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "$title (${players.size})",
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
        Icon(
            imageVector = if (contentOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }

    AnimatedVisibility(visible = contentOpen) {
        Column {
            players.forEach { player ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    text = player.name,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RoundTeamPreview() {
    AppTheme {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            RoundTeam(
                team = Team(
                    id = "1",
                    name = "Time Azul",
                    schema = TeamSchema(
                        goalKeepers = emptyList(),
                        startPlaying = emptyList(),
                        substitutes = emptyList(),
                        replacementSuggestions = emptyList()
                    )
                )
            )
        }
    }
}