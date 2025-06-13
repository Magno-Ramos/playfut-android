package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.DrawCard
import com.magnus.playfut.ui.features.rounds.playing.components.WinnerCard
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayingResultViewState
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit

@Composable
fun RoundPlayingResultScreen(viewModel: RoundPlayingViewModel) {
    val context = LocalContext.current
    val winnerViewState = viewModel.winnerViewState

    fun finish() {
        context.activity?.finish()
    }

    if (winnerViewState == null) {
        finish()
        return
    }

    Scaffold(
        topBar = { AppToolbar(title = "Fim da Rodada", onClickBack = ::finish) },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { finish() }
                ) {
                    Text(text = "Voltar ao Início")
                }
            }
        }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            when (val state = winnerViewState) {
                is RoundPlayingResultViewState.Draw -> DrawCard(
                    groupName = state.groupName,
                    teams = state.teams,
                    date = state.date
                )

                is RoundPlayingResultViewState.Victory -> {
                    WinnerCard(
                        groupName = state.groupName,
                        teamName = state.teamName,
                        wins = state.wins,
                        draws = state.draws,
                        losses = state.losses,
                        goalsScored = state.goalsScored,
                        goalsConceded = state.goalsConceded,
                        date = state.date,
                    )

                    KonfettiView(
                        modifier = Modifier.fillMaxSize(),
                        parties = listOf(
                            Party(
                                emitter = Emitter(duration = 7, TimeUnit.SECONDS).perSecond(50),
                                angle = 270, // 270° = para baixo
                                spread = 360, // espalha na horizontal
                                speed = 25f,
                                maxSpeed = 4f,
                                damping = 0.9f,
                                shapes = listOf(Shape.Circle, Shape.Square),
                                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                                position = Position.Relative(0.5, 0.0), // meio do topo
                                size = listOf(Size.SMALL, Size.MEDIUM)
                            )
                        )
                    )
                }

                else -> Unit
            }
        }
    }
}