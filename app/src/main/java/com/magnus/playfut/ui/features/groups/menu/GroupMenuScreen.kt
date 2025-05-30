package com.magnus.playfut.ui.features.groups.menu

import androidx.compose.foundation.layout.padding
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
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.groups.form.GroupsFormActivity
import com.magnus.playfut.ui.features.groups.menu.components.GroupMenu
import com.magnus.playfut.ui.features.groups.settings.GroupSettingsActivity
import com.magnus.playfut.ui.features.player.list.PlayerListActivity
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortActivity
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

    fun openNewRound() {
        val intent = RoundSortActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    fun openRoundsHistory() {
        // open rounds history
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
        val intent = GroupsFormActivity.createIntentToEdit(context, groupId, title)
        context.startActivity(intent)
    }

    Scaffold(
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