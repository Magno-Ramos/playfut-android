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
import androidx.compose.ui.platform.LocalContext
import com.magnus.playfut.ui.features.groups.form.GroupsFormActivity
import com.magnus.playfut.ui.features.home.HomeMenu
import com.magnus.playfut.ui.features.home.components.BottomNavigationBarContent
import com.magnus.playfut.ui.features.home.components.TopAppBarContent

@Composable
fun HomeScreen() {
    var currentScreen by remember { mutableStateOf(HomeMenu.Groups) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBarContent(
                title = when (currentScreen) {
                    HomeMenu.Groups -> "Grupos"
                    HomeMenu.Account -> "Conta"
                },
                onClickNotification = {
                    // TODO, implement notifications
                },
                onClickCreate = {
                    val intent = GroupsFormActivity.createIntent(context)
                    context.startActivity(intent)
                }
            )
        },
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



