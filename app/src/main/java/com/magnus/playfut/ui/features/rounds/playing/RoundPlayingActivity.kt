package com.magnus.playfut.ui.features.rounds.playing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.magnus.playfut.ui.features.rounds.playing.screens.RoundPlayingHomeScreen
import com.magnus.playfut.ui.theme.AppTheme

class RoundPlayingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { RoundPlayingScreen() }
    }
}

@Composable
private fun RoundPlayingScreen() {
    AppTheme {
        RoundPlayingNavigation()
    }
}

@Composable
private fun RoundPlayingNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RoundPlayingRoutes.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(RoundPlayingRoutes.Home.route) {
            RoundPlayingHomeScreen()
        }
    }
}
