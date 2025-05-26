package com.magnus.playfut.ui.features.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.magnus.playfut.ui.features.home.components.BottomNavigationBarContent
import com.magnus.playfut.ui.features.home.components.CreateGroupButton
import com.magnus.playfut.ui.features.home.components.EmptyContent
import com.magnus.playfut.ui.features.home.components.TopAppBarContent
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme

private enum class HomeMenu {
    Groups,
    Account
}

@Composable
fun HomeScreen() {
    val screens = listOf(HomeScreenGroups, HomeScreenAccount)
    var currentScreen by remember { mutableStateOf<Screen>(HomeScreenGroups) }
    var previousIndex by remember { mutableIntStateOf(0) }

    AppTheme {
        Scaffold(
            topBar = { TopAppBarContent() },
            bottomBar = {
                BottomNavigationBarContent(
                    currentScreen = currentScreen,
                    onClickMenu = {
                        currentScreen = when (it) {
                            HomeMenu.Groups -> HomeScreenGroups
                            HomeMenu.Account -> HomeScreenAccount
                        }
                    }
                )
            },
            containerColor = AppColor.bgPrimary
        ) { innerPaddings ->
            Box(
                modifier = Modifier
                    .padding(paddingValues = innerPaddings)
                    .fillMaxSize()
            ) {
                AnimatedContent(
                    label = "screen transition",
                    targetState = currentScreen,
                    transitionSpec = {
                        if (screens.indexOf(targetState) > previousIndex) {
                            slideInHorizontally { it } + fadeIn() togetherWith
                                    slideOutHorizontally { -it } + fadeOut()
                        } else {
                            slideInHorizontally { -it } + fadeIn() togetherWith
                                    slideOutHorizontally { it } + fadeOut()
                        }
                    }
                ) { screen -> screen.Content() }
            }
        }
    }
}

object HomeScreenGroups : Screen {
    @Composable
    override fun Content() {
        Column {
            EmptyContent(Modifier.weight(1f))
            CreateGroupButton(
                modifier = Modifier.padding(16.dp),
                onClick = {}
            )
        }
    }
}

object HomeScreenAccount : Screen {
    @Composable
    override fun Content() {
        Text(text = "Account Screen")
    }
}



