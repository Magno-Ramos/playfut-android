package com.magnus.playfut.ui.features.rounds.playing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.magnus.playfut.ui.features.rounds.playing.screens.RoundPlayingHomeScreen
import com.magnus.playfut.ui.features.rounds.playing.screens.RoundPlayingMatchScreen
import com.magnus.playfut.ui.features.rounds.playing.screens.RoundPlayingMatchSelectorScreen
import com.magnus.playfut.ui.features.rounds.playing.screens.RoundPlayingResultScreen
import com.magnus.playfut.ui.features.rounds.playing.screens.RoundPlayingTeamScreen
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
    val animationDuration = 400

    NavHost(
        navController = navController,
        startDestination = RoundPlayingRoutes.Home.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(animationDuration)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(animationDuration)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(animationDuration)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(animationDuration)
            )
        }
    ) {
        composable(RoundPlayingRoutes.Home.route) {
            RoundPlayingHomeScreen(
                roundId = roundId,
                viewModel = viewModel,
                navController = navController
            )
        }

        composable (RoundPlayingRoutes.MatchSelection.route) {
            RoundPlayingMatchSelectorScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(RoundPlayingRoutes.Match.route) {
            RoundPlayingMatchScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable (
            route = RoundPlayingRoutes.TeamDetail.route,
            arguments = listOf(
                navArgument("teamId") { type = NavType.StringType },
                navArgument("roundId") { type = NavType.StringType },
            )
        ) {
            RoundPlayingTeamScreen(
                teamId = it.arguments?.getString("teamId").orEmpty(),
                roundId = it.arguments?.getString("roundId").orEmpty(),
                navController = navController
            )
        }

        composable (RoundPlayingRoutes.RoundResult.route) {
            RoundPlayingResultScreen(
                viewModel = viewModel
            )
        }
    }
}
