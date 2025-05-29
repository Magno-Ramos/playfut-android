package com.magnus.playfut.ui.features.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.magnus.playfut.ui.features.home.HomeMenu

@Composable
fun BottomNavigationBarContent(
    currentScreen: HomeMenu,
    onClickMenu: (HomeMenu) -> Unit
) {
//    val itemColors = NavigationBarItemDefaults.colors(
//        selectedIconColor = onPrimaryLight,
//        unselectedIconColor = secondaryLight,
//        indicatorColor = AppColor.primary
//    )

    NavigationBar {
        NavigationBarItem(
            selected = currentScreen == HomeMenu.Groups,
            onClick = { onClickMenu(HomeMenu.Groups) },
            icon = { Icon(Icons.Outlined.People, contentDescription = null) },
            label = { Text("Grupos") }
        )
        NavigationBarItem(
            selected = currentScreen == HomeMenu.Account,
            onClick = { onClickMenu(HomeMenu.Account) },
            icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null) },
            label = { Text("Conta") }
        )
    }
}