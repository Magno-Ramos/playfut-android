package com.magnus.playfut.ui.features.rounds.sorting.form

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortPlayerSelectionActivity.Companion.EXTRA_PLAYERS_LIST
import com.magnus.playfut.ui.features.rounds.sorting.form.model.SelectablePlayer
import com.magnus.playfut.ui.theme.AppTheme

@Composable
fun RoundSortPlayerSelectionScreen(
    selectedPlayers: List<SelectablePlayer>,
) {
    val context = LocalContext.current

    val players = remember {
        mutableStateListOf<SelectablePlayer>().apply {
            addAll(selectedPlayers)
        }
    }

    fun closeScreen() {
        context.activity?.run {
            val mPlayers = ArrayList<SelectablePlayer>()
            mPlayers.addAll(players)

            val intent = Intent()
            intent.putExtra(EXTRA_PLAYERS_LIST, mPlayers)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    fun toggleSelection(player: SelectablePlayer) {
        players.replaceAll {
            if (it.id == player.id) {
                it.copy(selected = !it.selected)
            } else {
                it
            }
        }
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
            items(players) { player ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .padding(8.dp, 4.dp),
                ) {
                    Checkbox(
                        checked = player.selected,
                        onCheckedChange = { toggleSelection(player) }
                    )
                    Text(
                        text = player.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (!player.selected) TextDecoration.LineThrough else null
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RoundSortPlayerSelectionScreenPreview() {
    AppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            RoundSortPlayerSelectionScreen(
                selectedPlayers = listOf(
                    SelectablePlayer(name = "Player 1"),
                    SelectablePlayer(name = "Player 2"),
                    SelectablePlayer(name = "Player 3"),
                    SelectablePlayer(name = "Player 4"),
                )
            )
        }
    }
}