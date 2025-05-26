package com.magnus.playfut.ui.features.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.magnus.playfut.ui.features.home.HomeMenu
import com.magnus.playfut.ui.features.home.HomeScreenAccount
import com.magnus.playfut.ui.features.home.HomeScreenGroups
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.Color

@Composable
fun BottomNavigationBarContent(
    currentScreen: Screen,
    onClickMenu: (HomeMenu) -> Unit
) {
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.onPrimaryLight,
        unselectedIconColor = Color.secondaryLight,
        indicatorColor = AppColor.primary
    )

    NavigationBar(containerColor = AppColor.bgSecondary) {
        NavigationBarItem(
            selected = currentScreen == HomeScreenGroups,
            onClick = { onClickMenu(HomeMenu.Groups) },
            icon = { Icon(Icons.Outlined.People, contentDescription = null) },
            label = { Text("Grupos") },
            colors = itemColors,
        )
        NavigationBarItem(
            selected = currentScreen == HomeScreenAccount,
            onClick = { onClickMenu(HomeMenu.Account) },
            icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null) },
            label = { Text("Conta") },
            colors = itemColors
        )
    }
}