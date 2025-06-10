package com.magnus.playfut.ui.features.rounds.playing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.R
import com.magnus.playfut.ui.features.common.InputSelect
import com.magnus.playfut.ui.features.rounds.playing.states.RoundPlayerItem
import com.magnus.playfut.ui.features.rounds.playing.states.RoundTeamItem
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun GoalRegisterForm(
    players: List<RoundPlayerItem>,
    teams: List<RoundTeamItem>,
    onClickRegisterGoal: (RoundPlayerItem, RoundTeamItem) -> Unit = { _, _ -> }
) {
    var playerOptions by remember {
        mutableStateOf(
            players.filter { it.teamId == teams.first().id }
                .map { it.playerName to it.playerId }
        )
    }
    var selectedPlayer by remember { mutableStateOf<String?>(null) }

    val teamsOptions = teams.map { it.name to it.id }
    var selectedTeam by remember { mutableStateOf(teamsOptions.first().first) }

    fun onSelectPlayer(id: String) {
        selectedPlayer = players.first { it.playerId == id }.playerName
    }

    fun onSelectTeam(id: String) {
        val team = teams.first { it.id == id }
        selectedTeam = team.name
        playerOptions = players.filter { it.teamId == team.id }.map { it.playerName to it.playerId }
        selectedPlayer = playerOptions.firstOrNull()?.first
    }

    fun onClickRegister() {
        val player = players.first { it.playerName == selectedPlayer }
        val team = teams.first { it.name == selectedTeam }
        onClickRegisterGoal(player, team)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(MaterialTheme.spacing.medium),
    ) {
        Text(
            text = "Registrar Gol",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            InputSelect<String>(
                modifier = Modifier.weight(1f),
                inputModifier = Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(8.dp)
                ),
                options = teamsOptions,
                label = "Para:",
                selectedOption = selectedTeam,
                onOptionSelected = ::onSelectTeam
            )
            InputSelect<String>(
                modifier = Modifier.weight(1f),
                inputModifier = Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(8.dp)
                ),
                options = playerOptions,
                label = "Marcador:",
                selectedOption = selectedPlayer,
                onOptionSelected = ::onSelectPlayer
            )
        }
        Button(
            onClick = { onClickRegister() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.soccer_ball_net),
                contentDescription = null
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun GoalRegisterFormPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            GoalRegisterForm(
                players = listOf(
                    RoundPlayerItem(
                        teamId = "1",
                        playerId = "1",
                        playerName = "Jogador 1"
                    ),
                    RoundPlayerItem(
                        teamId = "2",
                        playerId = "2",
                        playerName = "Jogador 2"
                    )
                ),
                teams = listOf(
                    RoundTeamItem(
                        id = "1",
                        name = "Time 1",
                        victories = 1
                    ),
                    RoundTeamItem(
                        id = "2",
                        name = "Time 2",
                        victories = 2
                    )
                )
            )
        }
    }
}