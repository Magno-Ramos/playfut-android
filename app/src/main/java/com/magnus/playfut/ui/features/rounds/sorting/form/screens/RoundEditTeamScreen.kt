package com.magnus.playfut.ui.features.rounds.sorting.form.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.TextInput
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel

@Composable
fun RoundEditTeamScreen(
    viewModel: RoundSortViewModel,
    navController: NavController
) {
    var teamName by remember { mutableStateOf(viewModel.editableTeam?.teamName ?: "") }

    fun onClickSave() {
        val editableTeam = viewModel.editableTeam ?: return
        val editableTeamIndex = viewModel.distributorTeamSchema?.indexOf(editableTeam)

        if (editableTeamIndex == null || editableTeamIndex == -1) {
            return
        }

        val teams = viewModel.distributorTeamSchema?.toMutableList()
        teams?.removeAt(editableTeamIndex)
        teams?.add(editableTeamIndex, editableTeam.copy(teamName = teamName))
        viewModel.distributorTeamSchema = teams
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Editar time",
                onClickBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Button(
                    enabled = teamName.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { onClickSave() }
                ) {
                    Text(text = "Salvar")
                }
            }
        }
    ) { paddings ->
        Column(Modifier.padding(paddings).padding(16.dp)) {
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                value = teamName,
                onChangeValue = { teamName = it },
                label = "Editar nome do time",
                requestFocus = true
            )
        }
    }
}