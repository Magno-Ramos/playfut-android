package com.magnus.playfut.ui.features.rounds.playing.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.magnus.playfut.R
import com.magnus.playfut.domain.helper.RotationGenerator
import com.magnus.playfut.domain.model.relations.TeamWithSchema
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.state.StateHandler
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.common.ErrorView
import com.magnus.playfut.ui.features.common.Expandable
import com.magnus.playfut.ui.features.common.LoadingView
import com.magnus.playfut.ui.features.common.SubstitutionPlan
import com.magnus.playfut.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoundPlayingTeamScreen(
    viewModel: RoundPlayingTeamViewModel = koinViewModel(),
    teamId: String,
    roundId: String,
    navController: NavController
) {
    val teamState by viewModel.teamState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchTeam(teamId, roundId)
    }

    Scaffold(
        topBar = { AppToolbar(title = "Detalhes do Time", onClickBack = { navController.popBackStack() }) }
    ) { paddings ->
        Box(Modifier.padding(paddings)) {
            StateHandler(teamState) {
                loading { LoadingView() }
                error { ErrorView() }
                success { Content(it) }
            }
        }
    }
}

@Composable
private fun Content(team: TeamWithSchema) {
    val scrollState = rememberScrollState()
    val substitutions = RotationGenerator.generate(team.startPlaying, team.substitutes)
    val substitutionsGrouped = RotationGenerator.groupSubstitutionsByRounds(substitutions, team.substitutes.size)

    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .clip(RoundedCornerShape(8.dp))
            .verticalScroll(scrollState)
    ) {
        TeamHeader(teamName = team.teamName)
        PlayerList(title = "Goleiros", team.goalKeepers)
        PlayerList(title = "Titulares", team.startPlaying)
        PlayerList(title = "Reservas", team.substitutes)

        Expandable(title = "Plano de substituições (${substitutionsGrouped.size})", initExpanded = substitutionsGrouped.isNotEmpty()) {
            SubstitutionPlan(substitutionsGrouped)
        }
    }
}

@Composable
private fun TeamHeader(teamName: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium),
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.apparel),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            modifier = Modifier.weight(1f),
            text = teamName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun PlayerList(title: String, list: List<Player>) {
    Expandable(title = "$title (${list.size})", initExpanded = list.isNotEmpty()) {
        Column {
            list.forEach { player ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small),
                    text = player.name,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}