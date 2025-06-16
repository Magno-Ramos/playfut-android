package com.magnus.playfut.ui.features.rounds.sorting.form.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.magnus.playfut.domain.model.structure.PlayerType
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.model.SelectablePlayer
import com.magnus.playfut.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoundSortPlayerSelectionScreen(
    viewModel: RoundSortViewModel = koinViewModel(),
    navController: NavController
) {
    val selectablePlayers by viewModel.selectablePlayers.collectAsStateWithLifecycle()

    fun closeScreen() {
        navController.popBackStack()
    }

    fun toggleSelection(player: SelectablePlayer) {
        viewModel.toggleSelection(player)
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Selecione os jogadores",
                onClickBack = { closeScreen() }
            )
        }
    ) { paddings ->
        LazyColumn(
            modifier = Modifier.padding(paddings).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectablePlayers.sortedBy { it.type.ordinal }) { player ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainer, CircleShape)
                        .padding(MaterialTheme.spacing.small, MaterialTheme.spacing.tiny),
                ) {
                    Checkbox(
                        checked = player.selected,
                        onCheckedChange = { toggleSelection(player) }
                    )
                    Text(
                        text = if (player.type == PlayerType.GUEST) "${player.name} (Convidado)" else player.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}