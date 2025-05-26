package com.magnus.playfut.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.features.home.components.BottomNavigationBarContent
import com.magnus.playfut.ui.features.home.components.CreateGroupButton
import com.magnus.playfut.ui.features.home.components.EmptyContent
import com.magnus.playfut.ui.features.home.components.GroupItem
import com.magnus.playfut.ui.features.home.components.TopAppBarContent
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    var currentScreen by remember { mutableStateOf<HomeMenu>(HomeMenu.Groups) }

    AppTheme {
        Scaffold(
            containerColor = AppColor.bgPrimary,
            topBar = { TopAppBarContent() },
            bottomBar = {
                BottomNavigationBarContent(
                    currentScreen = currentScreen,
                    onClickMenu = { currentScreen = it }
                )
            }
        ) { innerPaddings ->
            Box(
                modifier = Modifier
                    .padding(paddingValues = innerPaddings)
                    .fillMaxSize()
            ) {
                when (currentScreen) {
                    HomeMenu.Groups -> HomeScreenGroups()
                    HomeMenu.Account -> HomeScreenAccount()
                }
            }
        }
    }
}

@Composable
private fun HomeScreenGroups(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchGroups()
    }

    when (uiState) {
        is UiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val groups = (uiState as UiState.Success<List<Group>>).data

            if (groups.isEmpty()) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    EmptyContent(Modifier.weight(1f))
                    CreateGroupButton(onClick = {})
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(groups) { GroupItem(group = it) }
                    item { CreateGroupButton(onClick = {}) }
                }
            }
        }

        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Erro: $message")
            }
        }
    }
}

@Composable
private fun HomeScreenAccount() {
    Text(text = "Account Screen")
}



