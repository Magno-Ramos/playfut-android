package com.magnus.playfut.ui.features.statistic.ranking

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magnus.playfut.domain.state.StateHandler
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.statistic.components.StatisticsArtilleryRanking
import com.magnus.playfut.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArtilleryRankingScreen(
    groupId: String,
    viewModel: RankingViewModel = koinViewModel()
) {
    val scrollState = rememberScrollState()
    val artilleryState by viewModel.artilleryState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchArtillery(groupId)
    }

    StateHandler(artilleryState) {
        loading { LoadingView() }
        error { ErrorView() }
        success { data ->
            StatisticsArtilleryRanking(
                list = data,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(MaterialTheme.spacing.medium),
            )
        }
    }
}