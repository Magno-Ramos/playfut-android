package com.magnus.playfut.ui.features.home.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.magnus.playfut.ui.features.home.HomeMenu
import com.magnus.playfut.ui.features.home.components.BottomNavigationBarContent
import com.magnus.playfut.ui.features.home.components.TopAppBarContent
import com.magnus.playfut.ui.theme.AppColor

@Composable
fun HomeScreen() {
    var currentScreen by remember { mutableStateOf<HomeMenu>(HomeMenu.Groups) }

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



