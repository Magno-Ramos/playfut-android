package com.magnus.playfut.ui.features.rounds.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.magnus.playfut.domain.model.relations.RoundResult
import com.magnus.playfut.domain.state.UiState
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun RoundHistoryScreen(
    viewModel: RoundHistoryViewModel = koinViewModel(),
    groupId: String
) {
    val context = LocalContext.current
    val state by viewModel.historyState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchHistory(groupId)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Histórico de rodadas",
                onClickBack = { context.activity?.onBackPressedDispatcher?.onBackPressed() })
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            when (val value = state) {
                UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView("Desculpe, ocorreu um erro")
                is UiState.Success<List<RoundResult>> -> Content(value.data)
            }
        }
    }
}

@Composable
private fun Content(data: List<RoundResult>) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(MaterialTheme.spacing.medium)
    ) {

        if (data.isEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
                    .padding(MaterialTheme.spacing.medium),
                text = "Nenhuma rodada jogada ainda",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        data.forEach {
            RoundResultItem(it)
        }
    }
}

@Composable
private fun RoundResultItem(item: RoundResult) {
    val roundName = "Rodada ${item.id.toInt() + 1}"
    val date = DateTimeFormatter
        .ofPattern("dd/MM/yyyy")
        .format(item.date.toInstant().atZone(ZoneId.systemDefault()))

    val description = if (item.winnerTeam != null) {
        "Vencedor: ${item.winnerTeam.name}"
    } else {
        "Empate"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp))
            .padding(MaterialTheme.spacing.medium, MaterialTheme.spacing.small)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = roundName,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = date,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = description,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}