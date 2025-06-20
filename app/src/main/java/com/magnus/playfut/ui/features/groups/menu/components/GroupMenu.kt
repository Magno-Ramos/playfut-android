package com.magnus.playfut.ui.features.groups.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.magnus.playfut.R
import com.magnus.playfut.domain.model.relations.GroupWithOpenedRound
import com.magnus.playfut.ui.features.common.MenuItem
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun GroupMenu(
    modifier: Modifier = Modifier,
    group: GroupWithOpenedRound,
    openNewRound: () -> Unit = {},
    openPlayers: () -> Unit = {},
    openSettings: () -> Unit = {},
    openEditGroup: () -> Unit = {},
    openRoundsHistory: () -> Unit = {},
    openStatistics: () -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GroupHeader(
            groupName = group.name,
            playersCount = group.playersCount,
            roundsCount = group.roundsCount
        )
        Spacer(Modifier.height(MaterialTheme.spacing.micro))
        MenuItem(
            icon = if (group.hasOpenedRound()) {
                rememberVectorPainter(Icons.Default.Flag)
            } else {
                painterResource(R.drawable.ball_soccer)
            },
            title = if (group.hasOpenedRound()) "Continuar Rodada" else "Nova Rodada",
            isPrimary = group.hasOpenedRound(),
            onClick = { openNewRound() }
        )
        MenuItem(
            icon = rememberVectorPainter(Icons.Outlined.QueryStats),
            title = "Estatísticas",
            onClick = { openStatistics() }
        )
        MenuItem(
            icon = rememberVectorPainter(Icons.Outlined.PeopleAlt),
            title = "Jogadores",
            onClick = { openPlayers() }
        )
        MenuItem(
            icon = rememberVectorPainter(Icons.Default.History),
            title = "Histórico de rodadas",
            onClick = { openRoundsHistory() },
        )
        MenuItem(
            icon = rememberVectorPainter(Icons.Outlined.Edit),
            title = "Editar Grupo",
            onClick = { openEditGroup() }
        )
        MenuItem(
            icon = rememberVectorPainter(Icons.Outlined.Settings),
            title = "Configurações",
            onClick = { openSettings() }
        )
    }
}

@PreviewLightDark
@Composable
private fun GroupMenuPreview() {
    AppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            GroupMenu(
                group = GroupWithOpenedRound(
                    name = "Grupo 1",
                    playersCount = 1,
                    roundsCount = 1,
                    openedRound = null
                )
            )
        }
    }
}