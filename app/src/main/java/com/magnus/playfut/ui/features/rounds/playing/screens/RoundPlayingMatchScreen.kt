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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.magnus.playfut.ui.domain.model.structure.Player
import com.magnus.playfut.ui.domain.state.asSuccess
import com.magnus.playfut.ui.features.common.AppAlertDialog
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.FinishMatch
import com.magnus.playfut.ui.features.rounds.playing.components.GoalRegisterForm
import com.magnus.playfut.ui.features.rounds.playing.components.MatchScore
import com.magnus.playfut.ui.features.rounds.playing.components.ScoreList
import com.magnus.playfut.ui.features.rounds.playing.components.Stopwatch
import com.magnus.playfut.ui.features.rounds.playing.components.TeamSelector
import com.magnus.playfut.ui.features.rounds.playing.states.RoundRemoveGoal
import com.magnus.playfut.ui.features.rounds.playing.states.RoundScoreItem
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import com.magnus.playfut.ui.theme.spacing
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoundPlayingMatchScreen(
    viewModel: RoundPlayingViewModel = koinViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val roundState by viewModel.roundState.collectAsState()

    val teams = roundState.asSuccess()?.data?.teams.orEmpty()
    val players = roundState.asSuccess()?.data?.players.orEmpty()

    var removeGoalConfirmation by remember { mutableStateOf(RoundRemoveGoal(false)) }

    var timeInSeconds by remember { mutableLongStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }

    var homeSelected by remember { mutableStateOf(teams[0]) }
    var awaySelected by remember { mutableStateOf(teams[1]) }

    var scores by remember { mutableStateOf(listOf<RoundScoreItem>()) }
    var homeScore = scores.count { it.teamId == homeSelected.id }
    var awayScore = scores.count { it.teamId == awaySelected.id }

    val homeOptions = teams.filter { it != homeSelected }.map { it.name to it.id }
    val awayOptions = teams.filter { it != awaySelected }.map { it.name to it.id }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            timeInSeconds++
        }
    }

    fun onSelectHomeTeam(id: String) {
        homeSelected = teams.first { it.id == id }
    }

    fun onSelectAwayTeam(id: String) {
        awaySelected = teams.first { it.id == id }
    }

    fun onClickRegisterGoal(player: Player, team: RoundTeamItem) {
        scores = scores + RoundScoreItem(
            playerId = player.id,
            teamId = team.id,
            playerName = player.name,
            teamName = team.name
        )
    }

    fun onStopwatchReset() {
        isRunning = false
        timeInSeconds = 0L
    }

    fun onStopwatchToggle() {
        isRunning = !isRunning
    }

    fun onClickRemoveGoal(score: RoundScoreItem) {
        removeGoalConfirmation = removeGoalConfirmation.copy(isVisible = true, score)
    }

    fun onClickCancelRemoveGoal() {
        removeGoalConfirmation = removeGoalConfirmation.copy(isVisible = false)
    }

    fun onClickConfirmRemoveGoal() {
        val item = removeGoalConfirmation.scoreItem
        if (item != null) {
            scores = scores - item
        }
        removeGoalConfirmation = removeGoalConfirmation.copy(isVisible = false)
    }

    fun onClickFinishMatch() {

    }

    Scaffold(
        topBar = {
            AppToolbar(title = "Partida")
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
            TeamSelector(
                homeTeam = homeSelected,
                awayTeam = awaySelected,
                homeOptions = homeOptions,
                awayOptions = awayOptions,
                onSelectHomeTeam = ::onSelectHomeTeam,
                onSelectAwayTeam = ::onSelectAwayTeam
            )
            Stopwatch(
                timeInSeconds = timeInSeconds,
                isRunning = isRunning,
                onReset = ::onStopwatchReset,
                onToggle = ::onStopwatchToggle
            )
            MatchScore(
                homeTeam = homeSelected.name,
                awayTeam = awaySelected.name,
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