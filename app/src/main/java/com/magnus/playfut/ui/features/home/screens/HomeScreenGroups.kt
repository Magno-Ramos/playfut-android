package com.magnus.playfut.ui.features.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.groups.create.GroupsCreateActivity
import com.magnus.playfut.ui.features.home.HomeViewModel
import com.magnus.playfut.ui.features.home.components.CreateGroupButton
import com.magnus.playfut.ui.features.home.components.EmptyContent
import com.magnus.playfut.ui.features.home.components.GroupItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenGroups(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchGroups()
    }

    when (uiState) {
        is UiState.Loading -> LoadingView()
        is UiState.Success -> SuccessState(uiState as UiState.Success<List<Group>>)
        is UiState.Error -> ErrorView(message = "Desculpe, ocorreu um erro")
    }
}

@Composable
private fun SuccessState(state: UiState.Success<List<Group>>) {
    val context = LocalContext.current
    val groups = state.data

    fun onClickCreateGroup() {
        context.startActivity(GroupsCreateActivity.createIntent(context))
    }

    if (groups.isEmpty()) {
        GroupsStateEmpty(onClickCreateGroup = ::onClickCreateGroup)
    } else {
        GroupsStateList(
            groups = groups,
            onClickCreateGroup = ::onClickCreateGroup
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
    groups: List<Group>,
    onClickCreateGroup: () -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(groups) { group ->
            GroupItem(group = group)
        }
        item {
            CreateGroupButton(onClick = { onClickCreateGroup() })
        }
    }
}