package com.magnus.playfut.ui.features.rounds.playing

import android.content.Context
import android.content.Intent
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
import org.koin.androidx.compose.koinViewModel

class RoundPlayingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val roundId = intent.getStringExtra("roundId") ?: return finish()
        setContent { RoundPlayingScreen(roundId) }
    }

    companion object {
        private const val ROUND_ID = "roundId"
        fun createIntent(context: Context, roundId: String): Intent {
            return Intent(context, RoundPlayingActivity::class.java).apply {
                putExtra(ROUND_ID, roundId)
            }
        }
    }
}

@Composable
private fun RoundPlayingScreen(
    roundId: String,
    viewModel: RoundPlayingViewModel = koinViewModel()
) {
    AppTheme {
        RoundPlayingNavigation(roundId, viewModel)
    }
}

@Composable
private fun RoundPlayingNavigation(roundId: String, viewModel: RoundPlayingViewModel) {
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
            RoundPlayingHomeScreen(
                roundId = roundId,
                viewModel = viewModel
            )
        }
    }
}
