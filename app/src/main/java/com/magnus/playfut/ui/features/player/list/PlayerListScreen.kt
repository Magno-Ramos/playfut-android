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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerPosition
import com.magnus.playfut.domain.model.structure.PlayerType
import com.magnus.playfut.domain.state.StateHandler
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.player.form.PlayerFormActivity
import com.magnus.playfut.ui.features.player.list.components.PlayerGroup
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerListScreen(
    viewModel: PlayerListViewModel = koinViewModel(),
    groupId: String
) {
    val context = LocalContext.current
    val playerListState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    var filterPlayerType by rememberSaveable { mutableStateOf(PlayerType.MEMBER) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.fetchPlayers(groupId)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    fun closeScreen() {
        context.activity?.onBackPressedDispatcher?.onBackPressed()
    }

    fun openPlayerCreate() {
        val intent = PlayerFormActivity.createIntentToCreate(context, groupId, filterPlayerType)
        context.startActivity(intent)
    }

    fun openPlayerEdit(player: Player) {
        val intent = PlayerFormActivity.createIntentToEdit(context, player)
        context.startActivity(intent)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Jogadores",
                onClickBack = { closeScreen() }
            )
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { openPlayerCreate() }
                ) {
                    Text(
                        text = when (filterPlayerType) {
                            PlayerType.MEMBER -> "Adicionar membro"
                            PlayerType.GUEST -> "Adicionar convidado"
                        }
                    )
                }
            }
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            StateHandler(playerListState) {
                loading { LoadingView() }
                error { ErrorView(message = "Erro ao carregar os jogadores.") }
                success { players ->
                    Column {
                        PrimaryTabRow(selectedTabIndex = PlayerType.entries.indexOf(filterPlayerType)) {
                            PlayerType.entries.forEachIndexed { index, type ->
                                Tab(
                                    selected = filterPlayerType == type,
                                    onClick = { filterPlayerType = type },
                                    text = {
                                        Text(
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            text = when (type) {
                                                PlayerType.MEMBER -> "Membros"
                                                PlayerType.GUEST -> "Convidados"
                                            },
                                        )
                                    }
                                )
                            }
                        }

                        if (players.none { it.type == filterPlayerType }) {
                            PlayerListEmpty(
                                message = when (filterPlayerType) {
                                    PlayerType.MEMBER -> "Nenhum membro adicionado"
                                    PlayerType.GUEST -> "Nenhum convidado adicionado"
                                }
                            )
                        } else {
                            PlayerListContent(
                                players = players.filter { it.type == filterPlayerType },
                                onClickPlayer = { openPlayerEdit(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerListEmpty(message: String = "Nenhum jogador adicionado") {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
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
        .groupBy { it.position }
        .toList()
        .sortedBy { if (it.first == PlayerPosition.GOALKEEPER) 0 else 1 }

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