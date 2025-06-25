package com.magnus.playfut.ui.features.rounds.playing.screens

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.magnus.playfut.R
import com.magnus.playfut.domain.model.form.MatchForm
import com.magnus.playfut.domain.model.form.MatchItemScore
import com.magnus.playfut.domain.state.asSuccess
import com.magnus.playfut.domain.state.isSuccess
import com.magnus.playfut.ui.features.common.AppAlertDialog
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.components.FinishMatch
import com.magnus.playfut.ui.features.rounds.playing.components.GoalCelebration
import com.magnus.playfut.ui.features.rounds.playing.components.GoalRegisterForm
import com.magnus.playfut.ui.features.rounds.playing.components.MatchScore
import com.magnus.playfut.ui.features.rounds.playing.components.ScoreList
import com.magnus.playfut.ui.features.rounds.playing.components.Stopwatch
import com.magnus.playfut.ui.features.rounds.playing.sheets.CloseMatchConfirmActionSheet
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayerItem
import com.magnus.playfut.ui.features.rounds.playing.states.RoundRemoveGoal
import com.magnus.playfut.ui.features.rounds.playing.states.RoundScoreItem
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import com.magnus.playfut.ui.theme.spacing
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundPlayingMatchScreen(
    viewModel: RoundPlayingViewModel = koinViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.cheer_sound) }
    var playGoalAnimation by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val roundState by viewModel.roundState.collectAsStateWithLifecycle()
    val closeMatchState by viewModel.closeMatchState.collectAsStateWithLifecycle()
    val allPlayers = roundState.asSuccess()?.data?.players.orEmpty()

    val closeMatchSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showCloseMatchModalBottomSheet by remember { mutableStateOf(false) }

    val homeTeam = viewModel.matchHomeTeam ?: return
    val awayTeam = viewModel.matchAwayTeam ?: return

    val teams = roundState.asSuccess()?.data?.teams.orEmpty()
        .filter { it.id == homeTeam.id || it.id == awayTeam.id }

    val players by remember { mutableStateOf(allPlayers) }
    var removeGoalConfirmation by remember { mutableStateOf(RoundRemoveGoal(false)) }

    var scores by remember { mutableStateOf(listOf<RoundScoreItem>()) }
    val homeScore = scores.count { it.teamId == homeTeam.id }
    val awayScore = scores.count { it.teamId == awayTeam.id }

    var timeInSeconds by remember { mutableLongStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            timeInSeconds++
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    if (closeMatchState.isSuccess()) {
        navController.popBackStack(navController.graph.startDestinationId, inclusive = false)
        viewModel.resetCloseMatchState()
    }

    fun applyFinishMatch() {
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

    fun onClickRegisterGoal(player: RoundPlayerItem, team: RoundTeamItem) {
        scores = scores + RoundScoreItem(
            playerId = player.playerId,
            teamId = team.id,
            playerName = player.playerName,
            teamName = team.name
        )

        if (mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(0)
        }

        // playGoalAnimation = true
        mediaPlayer.start()
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

    fun onClickDismissCloseMatchModalBottom() {
        scope.launch {
            closeMatchSheetState.hide()
        }.invokeOnCompletion {
            showCloseMatchModalBottomSheet = false
        }
    }

    fun onClickConfirmCloseMatch() {
        scope.launch {
            closeMatchSheetState.hide()
        }.invokeOnCompletion {
            showCloseMatchModalBottomSheet = false
            applyFinishMatch()
        }
    }

    fun onStopwatchReset() {
        isRunning = false
        timeInSeconds = 0L
    }

    fun onStopwatchToggle() {
        isRunning = !isRunning
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Partida",
                onClickBack = { navController.popBackStack() }
            )
        }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Stopwatch(
                    timeInSeconds = timeInSeconds,
                    isRunning = isRunning,
                    onReset = { onStopwatchReset() },
                    onToggle = { onStopwatchToggle() }
                )
                MatchScore(
                    homeTeam = homeTeam.name,
                    awayTeam = awayTeam.name,
                    homeScore = homeScore,
                    awayScore = awayScore
                )
                GoalRegisterForm(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                    players = players,
                    teams = teams,
                    onClickRegisterGoal = ::onClickRegisterGoal
                )
                ScoreList(
                    scores = scores,
                    onClickRemove = { onClickRemoveGoal(it) }
                )
                FinishMatch(onClickFinish = { showCloseMatchModalBottomSheet = true })
                Spacer(Modifier.height(MaterialTheme.spacing.medium))

                if (showCloseMatchModalBottomSheet) {
                    CloseMatchConfirmActionSheet(
                        homeTeamName = homeTeam.name,
                        awayTeamName = awayTeam.name,
                        homeScore = homeScore,
                        awayScore = awayScore,
                        onConfirm = { onClickConfirmCloseMatch() },
                        onDismiss = { onClickDismissCloseMatchModalBottom() },
                        sheetState = closeMatchSheetState
                    )
                }

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

            if (playGoalAnimation) {
                GoalCelebration(
                    modifier = Modifier.align(Alignment.Center),
                    isPlaying = playGoalAnimation,
                    onFinished = { playGoalAnimation = false },
                )
            }
        }
    }
}