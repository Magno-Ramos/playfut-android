package com.magnus.playfut.ui.features.rounds.sorting.form

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.magnus.playfut.ui.features.rounds.sorting.form.screens.RoundSortFormScreen
import com.magnus.playfut.ui.features.rounds.sorting.form.screens.RoundSortPlayerSelectionScreen
import com.magnus.playfut.ui.features.rounds.sorting.result.RoundSortResultScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoundSortScreen(
    viewModel: RoundSortViewModel = koinViewModel(),
    groupId: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RoundSortRoutes.Form.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable(RoundSortRoutes.Form.route) {
            RoundSortFormScreen(
                groupId = groupId,
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(RoundSortRoutes.PlayerSelection.route) {
            RoundSortPlayerSelectionScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(RoundSortRoutes.Result.route) {
            RoundSortResultScreen()
        }
    }
}