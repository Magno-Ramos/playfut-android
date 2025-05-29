package com.magnus.playfut.ui.features.groups.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun GroupMenu(
    modifier: Modifier = Modifier,
    group: Group,
    openNewRound: () -> Unit = {},
    openPlayers: () -> Unit = {},
    openSettings: () -> Unit = {},
    openEditGroup: () -> Unit = {},
    openRoundsHistory: () -> Unit = {},
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MenuItem(
            icon = Icons.Default.Sports,
            title = "Nova Rodada",
            subtitle = "Quem vencerá hoje?",
            isPrimary = true,
            onClick = { openNewRound() }
        )
        MenuItem(
            icon = Icons.Outlined.PeopleAlt,
            title = "Jogadores",
            subtitle = when {
                group.players.isEmpty() -> "Adicione jogadores do grupo"
                group.players.size == 1 -> "1 jogador adicionado"
                else -> "${group.players.size} jogadores"
            },
            onClick = { openPlayers() }
        )
        MenuItem(
            icon = Icons.Default.History,
            title = "Histórico de rodadas",
            onClick = { openRoundsHistory() },
            subtitle = when {
                group.rounds.isEmpty() -> "Nenhuma rodada realizada"
                group.rounds.size == 1 -> "1 rodada realizada"
                else -> "${group.rounds.size} rodadas realizadas"
            }
        )
        MenuItem(
            icon = Icons.Outlined.Edit,
            title = "Editar Grupo",
            onClick = { openEditGroup() }
        )
        MenuItem(
            icon = Icons.Outlined.Settings,
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
                group = Group(
                    name = "Grupo 1",
                    players = listOf(),
                    rounds = listOf()
                )
            )
        }
    }
}