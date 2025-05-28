package com.magnus.playfut.ui.features.groups.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.toPlayersCountString
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.groups.menu.components.MenuItem
import com.magnus.playfut.ui.features.groups.settings.GroupSettingsActivity
import com.magnus.playfut.ui.features.player.list.PlayerListActivity
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupMenuScreen(
    viewModel: GroupMenuViewModel = koinViewModel(),
    groupId: String,
    onClickBack: () -> Unit = {}
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    val groupState = viewModel.uiState.collectAsState()

    fun closeScreen() {
        context.activity?.onBackPressedDispatcher?.onBackPressed()
    }

    LaunchedEffect(Unit) {
        viewModel.observeGroup(groupId)
    }

    fun openSettings() {
        val intent = GroupSettingsActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    fun openPlayers() {
        val intent = PlayerListActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    Scaffold(
        containerColor = AppColor.bgPrimary,
        topBar = { AppToolbar(title = title, onClickBack = onClickBack) }
    ) { paddings ->

        when (val state = groupState.value) {
            UiState.Loading -> Unit
            is UiState.Error -> closeScreen()
            is UiState.Success -> {
                val group = state.data
                title = group.name
                GroupMenu(
                    modifier = Modifier.padding(paddings),
                    group = state.data,
                    openPlayers = { openPlayers() },
                    openSettings = { openSettings() }
                )
            }
        }
    }
}

@Composable
private fun GroupMenu(
    modifier: Modifier = Modifier,
    group: Group,
    openPlayers: () -> Unit = {},
    openSettings: () -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MenuItem(
            icon = Icons.Default.Sports,
            title = "Nova Rodada",
            subtitle = "Quem vencerá hoje?",
            isPrimary = true
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
            subtitle = when {
                group.rounds.isEmpty() -> "Nenhuma rodada realizada"
                group.rounds.size == 1 -> "1 rodada realizada"
                else -> "${group.rounds.size} rodadas realizadas"
            }
        )
        MenuItem(
            icon = Icons.Outlined.Edit,
            title = "Editar Grupo",
        )
        MenuItem(
            icon = Icons.Outlined.Delete,
            title = "Configurações",
            onClick = { openSettings() }
        )
    }
}

@Preview
@Composable
private fun GroupMenuPreview() {
    AppTheme {
        Box(Modifier.background(AppColor.bgPrimary)) {
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