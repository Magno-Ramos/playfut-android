package com.magnus.playfut.ui.features.player.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.player.create.PlayerCreateActivity
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
        topBar = { AppToolbar(title = "Jogadores", onClickBack = { closeScreen() }) },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.padding(16.dp),
                containerColor = AppColor.bgPrimary
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { openPlayerCreate() }
                ) {
                    Text(text = "Adicionar Jogador")
                }
            }
        }
    ) { paddings ->
        when (val state = groupState.value) {
            UiState.Loading -> LoadingView()
            is UiState.Error -> ErrorView(message = "Erro ao carregar os jogadores.")
            is UiState.Success<Group> -> {
                PlayerListContent(
                    modifier = Modifier.padding(paddings),
                    players = state.data.players
                )
            }
        }
    }
}

@Composable
private fun PlayerListContent(
    modifier: Modifier = Modifier,
    players: List<Player>
) {
    LazyColumn(modifier) {
        items(players) { player ->
            Text(text = "${player.name} - Qualidade: ${player.quality}")
        }
    }
}