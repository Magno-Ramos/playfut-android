package com.magnus.playfut.ui.features.home

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
import com.magnus.playfut.ui.features.home.components.BottomNavigationBarContent
import com.magnus.playfut.ui.features.home.components.CreateGroupButton
import com.magnus.playfut.ui.features.home.components.EmptyContent
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
    Column {
        EmptyContent(Modifier.weight(1f))
        CreateGroupButton(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
private fun HomeScreenAccount() {
    Text(text = "Account Screen")
}



