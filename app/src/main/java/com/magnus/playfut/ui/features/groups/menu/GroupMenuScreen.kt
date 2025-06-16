package com.magnus.playfut.ui.features.groups.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magnus.playfut.domain.model.relations.GroupWithOpenedRound
import com.magnus.playfut.domain.state.StateHandler
import com.magnus.playfut.domain.state.UiState
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.groups.form.GroupsFormActivity
import com.magnus.playfut.ui.features.groups.menu.components.GroupMenu
import com.magnus.playfut.ui.features.groups.settings.GroupSettingsActivity
import com.magnus.playfut.ui.features.player.list.PlayerListActivity
import com.magnus.playfut.ui.features.rounds.history.RoundHistoryActivity
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingActivity
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortActivity
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupMenuScreen(
    viewModel: GroupMenuViewModel = koinViewModel(),
    groupId: String,
    onClickBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val groupState by viewModel.uiState.collectAsStateWithLifecycle()
    var groupName by remember { mutableStateOf("") }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.fetchGroup(groupId)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    fun openNewRound() {
        (groupState as? UiState.Success<GroupWithOpenedRound>)?.data?.let { data ->
            if (data.hasOpenedRound()) {
                val roundId = data.openedRound?.id ?: return
                val intent = RoundPlayingActivity.createIntent(context, roundId)
                context.startActivity(intent)
            } else {
                val intent = RoundSortActivity.createIntent(context, groupId)
                context.startActivity(intent)
            }
        }
    }

    fun openRoundsHistory() {
        val intent = RoundHistoryActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    fun openSettings() {
        val intent = GroupSettingsActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    fun openPlayers() {
        val intent = PlayerListActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    fun openEditGroup() {
        val intent = GroupsFormActivity.createIntentToEdit(context, groupId, groupName)
        context.startActivity(intent)
    }

    Scaffold(
        topBar = { AppToolbar(title = groupName, onClickBack = onClickBack) }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            StateHandler(groupState) {
                loading { LoadingView() }
                error { ErrorView("Desculpe, ocorreu um erro") }
                content { group ->
                    GroupMenu(
                        group = group,
                        openNewRound = ::openNewRound,
                        openRoundsHistory = ::openRoundsHistory,
                        openPlayers = ::openPlayers,
                        openSettings = ::openSettings,
                        openEditGroup = ::openEditGroup
                    )
                }
            }
        }
    }
}