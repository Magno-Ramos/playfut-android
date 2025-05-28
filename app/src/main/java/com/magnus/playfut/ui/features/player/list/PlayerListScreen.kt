package com.magnus.playfut.ui.features.player.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.model.PlayerType
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.player.create.PlayerCreateActivity
import com.magnus.playfut.ui.features.player.list.components.PlayerGroup
import com.magnus.playfut.ui.theme.AppColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayerListScreen(
    viewModel: PlayerListViewModel = koinViewModel(),
    groupId: String
) {
    val context = LocalContext.current
    val groupState = viewModel.uiState.collectAsState()

    fun closeScreen() {
        context.activity?.onBackPressedDispatcher?.onBackPressed()
    }

    fun openPlayerCreate() {
        val intent = PlayerCreateActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    LaunchedEffect(Unit) {
        viewModel.observeGroup(groupId)
    }

    Scaffold(
        containerColor = AppColor.bgPrimary,
        topBar = {
            AppToolbar(
                title = "Jogadores",
                onClickBack = { closeScreen() },
                actions = {
                    IconButton(onClick = { openPlayerCreate() }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar Jogador"
                        )
                    }
                }
            )
        },
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            when (val state = groupState.value) {
                UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView(message = "Erro ao carregar os jogadores.")
                is UiState.Success<Group> -> {
                    if (state.data.players.isEmpty()) {
                        PlayerListEmpty()
                    } else {
                        PlayerListContent(players = state.data.players)
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerListEmpty() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nenhum jogador adicionado",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PlayerListContent(
    modifier: Modifier = Modifier,
    players: List<Player>
) {
    val groups = players
        .groupBy { it.type }
        .toList()
        .sortedBy { if (it.first == PlayerType.GOALKEEPER) 0 else 1 }

    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(groups) {
            PlayerGroup(
                type = it.first,
                group = it.second
            )
        }
    }
}