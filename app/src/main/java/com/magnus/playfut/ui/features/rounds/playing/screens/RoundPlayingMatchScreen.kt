package com.magnus.playfut.ui.features.rounds.playing.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.magnus.playfut.ui.features.rounds.playing.components.FinishMatch
import com.magnus.playfut.ui.features.rounds.playing.components.GoalRegisterForm
import com.magnus.playfut.ui.features.rounds.playing.components.GradientAppBar
import com.magnus.playfut.ui.features.rounds.playing.components.MatchScore
import com.magnus.playfut.ui.features.rounds.playing.components.Stopwatch
import com.magnus.playfut.ui.features.rounds.playing.components.TeamSelector
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun RoundPlayingMatchScreen() {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { GradientAppBar() }
    ) { paddings ->
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .padding(paddings)
                .padding(MaterialTheme.spacing.medium)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Stopwatch()
            TeamSelector()
            MatchScore()
            GoalRegisterForm()
            FinishMatch()
        }
    }
}

@Preview(
    showSystemUi = true,
    device = "id:medium_phone",
    apiLevel = 36
)
@Preview(
    showSystemUi = true,
    device = "id:medium_phone",
    apiLevel = 36,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun RoundPlayingMatchScreenPreview() {
    AppTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            RoundPlayingMatchScreen()
        }
    }
}