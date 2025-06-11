package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.magnus.playfut.ui.features.common.AppToolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoundPlayingTeamScreen(
    viewModel: RoundPlayingTeamViewModel = koinViewModel(),
    teamId: String,
    navController: NavController
) {

    LaunchedEffect(Unit) {
        viewModel.fetchTeam(teamId)
    }

    Scaffold(
        topBar = { AppToolbar(title = "Detalhes do Time", onClickBack = { navController.popBackStack() }) }
    ) { paddings ->
        Box(Modifier.padding(paddings)) {

        }
    }
}