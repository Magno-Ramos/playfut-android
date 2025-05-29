package com.magnus.playfut.ui.features.rounds.sorting.form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.model.Player
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.rounds.sorting.form.components.RoundSortingForm

@Composable
fun RoundSortFormScreen() {
    val context = LocalContext.current
    val players = listOf<Player>()
    val teamsCount = remember { mutableStateOf("") }
    val playersCount = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Sortear Times",
                onClickBack = { context.activity?.finish() }
            )
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(16.dp)) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = {}
                ) {
                    Text(text = "Sortear")
                }
            }
        }
    ) { paddings ->
        Box(Modifier.padding(paddings)) {
            RoundSortingForm(
                totalPlayers = "17",
                teamsCount = teamsCount.value,
                playersCount = playersCount.value,
                onChangeTeamsCount = { teamsCount.value = it },
                onChangePlayersCount = { playersCount.value = it },
            )
        }
    }
}