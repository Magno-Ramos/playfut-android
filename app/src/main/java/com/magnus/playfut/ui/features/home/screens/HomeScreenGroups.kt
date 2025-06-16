package com.magnus.playfut.ui.features.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magnus.playfut.domain.model.relations.GroupWithPlayersAndRoundsCount
import com.magnus.playfut.domain.state.StateHandler
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.groups.form.GroupsFormActivity
import com.magnus.playfut.ui.features.groups.menu.GroupMenuActivity
import com.magnus.playfut.ui.features.home.HomeViewModel
import com.magnus.playfut.ui.features.home.components.CreateGroupButton
import com.magnus.playfut.ui.features.home.components.EmptyContent
import com.magnus.playfut.ui.features.home.components.GroupItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenGroups(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.fetchGroups()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer = observer)
        }
    }

    fun onClickGroup(groupId: String) {
        val intent = GroupMenuActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    StateHandler(uiState) {
        loading { LoadingView() }
        error { ErrorView(message = "Desculpe, ocorreu um erro") }
        content { groups -> SuccessState(groups = groups, onClickGroup = ::onClickGroup) }
    }
}

@Composable
private fun SuccessState(
    groups: List<GroupWithPlayersAndRoundsCount>,
    onClickGroup: (String) -> Unit = {}
) {
    val context = LocalContext.current

    fun onClickCreateGroup() {
        context.startActivity(GroupsFormActivity.createIntent(context))
    }

    if (groups.isEmpty()) {
        GroupsStateEmpty(onClickCreateGroup = ::onClickCreateGroup)
    } else {
        GroupsStateList(
            groups = groups,
            onClickGroup = onClickGroup
        )
    }
}

@Composable
fun GroupsStateEmpty(
    modifier: Modifier = Modifier,
    onClickCreateGroup: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EmptyContent(Modifier.weight(1f))
        CreateGroupButton(onClick = { onClickCreateGroup() })
    }
}

@Composable
fun GroupsStateList(
    modifier: Modifier = Modifier,
    groups: List<GroupWithPlayersAndRoundsCount>,
    onClickGroup: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(groups) { group ->
            GroupItem(
                groupName = group.name,
                playersCount = group.playersCount,
                roundsCount = group.roundsCount,
                onClick = { onClickGroup(group.id) }
            )
        }
    }
}