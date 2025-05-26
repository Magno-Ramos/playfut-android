package com.magnus.playfut.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Group
import com.magnus.playfut.ui.features.home.components.BottomNavigationBarContent
import com.magnus.playfut.ui.features.home.components.CreateGroupButton
import com.magnus.playfut.ui.features.home.components.EmptyContent
import com.magnus.playfut.ui.features.home.components.GroupItem
import com.magnus.playfut.ui.features.home.components.TopAppBarContent
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

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
private fun HomeScreenGroups() {

    val groups: List<Group> = listOf(
        Group(
            id = "1",
            name = "Grupo 1",
            players = listOf("Jogador 1"),
            rounds = 2
        ),
        Group(
            id = "2",
            name = "Grupo 2",
            players = listOf("Jogador 3", "Jogador 4"),
            rounds = 1
        )
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        groups.forEach {
            GroupItem(group = it)
        }

        if (groups.isEmpty()) {
            EmptyContent(Modifier.weight(1f))
        }

        CreateGroupButton(
            onClick = {}
        )
    }
}

@Composable
private fun HomeScreenAccount() {
    Text(text = "Account Screen")
}



