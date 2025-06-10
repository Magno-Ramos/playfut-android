package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.magnus.playfut.ui.domain.model.form.MatchForm
import com.magnus.playfut.ui.domain.model.form.MatchItemScore
import com.magnus.playfut.ui.domain.state.asSuccess
import com.magnus.playfut.ui.domain.state.isSuccess
import com.magnus.playfut.ui.features.common.AppAlertDialog
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.FinishMatch
import com.magnus.playfut.ui.features.rounds.playing.components.GoalRegisterForm
import com.magnus.playfut.ui.features.rounds.playing.components.MatchScore
import com.magnus.playfut.ui.features.rounds.playing.components.ScoreList
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayerItem
import com.magnus.playfut.ui.features.rounds.playing.states.RoundRemoveGoal
import com.magnus.playfut.ui.features.rounds.playing.states.RoundScoreItem
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import com.magnus.playfut.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoundPlayingMatchScreen(
    viewModel: RoundPlayingViewModel = koinViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val roundState by viewModel.roundState.collectAsState()
    val closeMatchState by viewModel.closeMatchState.collectAsState()
    val allPlayers = roundState.asSuccess()?.data?.players.orEmpty()

    val homeTeam = viewModel.matchHomeTeam ?: return
    val awayTeam = viewModel.matchAwayTeam ?: return

    val teams = roundState.asSuccess()?.data?.teams.orEmpty()
    var players by remember { mutableStateOf(allPlayers) }
    var removeGoalConfirmation by remember { mutableStateOf(RoundRemoveGoal(false)) }

    var scores by remember { mutableStateOf(listOf<RoundScoreItem>()) }
    var homeScore = scores.count { it.teamId == homeTeam.id }
    var awayScore = scores.count { it.teamId == awayTeam.id }

    if (closeMatchState.isSuccess()) {
        navController.popBackStack(navController.graph.startDestinationId, inclusive = false)
        viewModel.resetCloseMatchState()
    }

    fun onClickRegisterGoal(player: RoundPlayerItem, team: RoundTeamItem) {
        scores = scores + RoundScoreItem(
            playerId = player.playerId,
            teamId = team.id,
            playerName = player.playerName,
            teamName = team.name
        )
    }

    fun onClickRemoveGoal(score: RoundScoreItem) {
        removeGoalConfirmation = removeGoalConfirmation.copy(isVisible = true, score)
    }

    fun onClickCancelRemoveGoal() {
        removeGoalConfirmation = removeGoalConfirmation.copy(isVisible = false)
    }

    fun onClickConfirmRemoveGoal() {
        removeGoalConfirmation = removeGoalConfirmation.copy(isVisible = false)
        val item = removeGoalConfirmation.scoreItem
        if (item != null) {
            scores = scores - item
        }
    }

    fun onClickFinishMatch() {
        viewModel.closeMatch(
            MatchForm(
                roundId = roundState.asSuccess()?.data?.roundId.orEmpty(),
                homeTeamId = homeTeam.id,
                awayTeamId = awayTeam.id,
                scores = scores.map {
                    MatchItemScore(
                        playerId = it.playerId,
                        scoredTeamId = it.teamId,
                        isOwnGoal = false
                    )
                }
            )
        )
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Partida",
                onClickBack = { navController.popBackStack() }
            )
        }
    ) { paddings ->
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .padding(paddings)
                .padding(MaterialTheme.spacing.medium)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            MatchScore(
                homeTeam = homeTeam.name,
                awayTeam = awayTeam.name,
                homeScore = homeScore,
                awayScore = awayScore
            )
            GoalRegisterForm(
                players = players,
                teams = teams,
                onClickRegisterGoal = ::onClickRegisterGoal
            )
            ScoreList(
                scores = scores,
                onClickRemove = { onClickRemoveGoal(it) }
            )
            FinishMatch(
                onClickFinish = ::onClickFinishMatch
            )

            if (removeGoalConfirmation.isVisible) {
                AppAlertDialog(
                    dialogTitle = "Remover Gol",
                    dialogText = "Tem certeza que deseja remover o gol?",
                    confirmButtonText = "Confirmar",
                    dismissButtonText = "Cancelar",
                    onDismissRequest = { onClickCancelRemoveGoal() },
                    onConfirmation = { onClickConfirmRemoveGoal() }
                )
            }
        }
    }
}