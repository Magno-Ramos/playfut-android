package com.magnus.playfut.ui.features.statistic.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magnus.playfut.domain.state.StateHandler
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.statistic.components.HomeArtilleryRanking
import com.magnus.playfut.ui.features.statistic.components.HomeHeader
import com.magnus.playfut.ui.features.statistic.components.HomeLabel
import com.magnus.playfut.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatisticHomeScreen(
    viewModel: StatisticHomeViewModel = koinViewModel(),
    groupId: String
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val homeScrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.fetchStatistics(groupId)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Estatísticas",
                onClickBack = { context.activity?.finish() }
            )
        }
    ) { paddings ->
        Box(Modifier.padding(paddings)) {
            StateHandler(uiState) {
                loading { LoadingView() }
                error { ErrorView() }
                success { data ->
                    Column(
                        modifier = Modifier
                            .verticalScroll(homeScrollState)
                            .padding(MaterialTheme.spacing.medium)
                    ) {
                        HomeLabel("Dados do Grupo")
                        HomeHeader(
                            totalRounds = data.totalRounds,
                            totalMatches = data.totalMatches,
                            totalGoals = data.totalGoals
                        )
                        Spacer(Modifier.height(MaterialTheme.spacing.large))
                        HomeLabel("Ranking de Artilharia")
                        HomeArtilleryRanking(
                            list = data.artilleryRanking,
                            maxCount = 3
                        ) { /* TODO add click here */ }
                    }
                }
            }
        }
    }
}