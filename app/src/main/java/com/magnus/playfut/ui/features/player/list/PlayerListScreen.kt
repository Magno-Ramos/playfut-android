package com.magnus.playfut.ui.features.player.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.magnus.playfut.ui.features.player.form.PlayerFormActivity
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
        val intent = PlayerFormActivity.createIntentToCreate(context, groupId)
        context.startActivity(intent)
    }

    fun openPlayerEdit(player: Player) {
        val intent = PlayerFormActivity.createIntentToEdit(context, player)
        context.startActivity(intent)
    }

    LaunchedEffect(Unit) {
        viewModel.observeGroup(groupId)
    }

    Scaffold(
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
        bottomBar = {
            BottomAppBar(
                containerColor = AppColor.bgPrimary,
                contentPadding = PaddingValues(horizontal =  16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    onClick = { openPlayerCreate() }
                ) {
                    Text(text = "Adicionar jogador")
                }
            }
        }
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
                        PlayerListContent(
                            players = state.data.players,
                            onClickPlayer = { openPlayerEdit(it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerListEmpty() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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
    players: List<Player>,
    onClickPlayer: (Player) -> Unit = {}
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
                group = it.second,
                onClickPlayer = onClickPlayer
            )
        }
    }
}