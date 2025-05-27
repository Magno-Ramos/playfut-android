package com.magnus.playfut.ui.features.groups.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.state.UiState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.groups.menu.components.MenuItem
import com.magnus.playfut.ui.features.groups.settings.GroupSettingsActivity
import com.magnus.playfut.ui.theme.AppColor
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupMenuScreen(
    viewModel: GroupMenuViewModel = koinViewModel(),
    groupId: String,
    onClickBack: () -> Unit = {}
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    val groupState = viewModel.uiState.collectAsState()

    fun closeScreen() {
        context.activity?.onBackPressedDispatcher?.onBackPressed()
    }

    LaunchedEffect(groupState.value) {
        when (val state = groupState.value) {
            UiState.Loading -> Unit
            is UiState.Error -> closeScreen()
            is UiState.Success -> title = state.data.name
        }
    }

    LaunchedEffect(Unit) {
        viewModel.observeGroup(groupId)
    }

    fun openSettings() {
        val intent = GroupSettingsActivity.createIntent(context, groupId)
        context.startActivity(intent)
    }

    Scaffold(
        containerColor = AppColor.bgPrimary,
        topBar = { AppToolbar(title = title, onClickBack = onClickBack) }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MenuItem(
                icon = Icons.Default.Sports,
                title = "Nova Rodada",
                subtitle = "Faça o sortei dos times para nova rodada",
                isPrimary = true
            )
            MenuItem(
                icon = Icons.Default.History,
                title = "Histórico de rodadas"
            )
            MenuItem(
                icon = Icons.Outlined.PeopleAlt,
                title = "Jogadores"
            )
            MenuItem(
                icon = Icons.Outlined.Edit,
                title = "Editar Grupo"
            )
            MenuItem(
                icon = Icons.Outlined.Delete,
                title = "Configurações",
                onClick = { openSettings() }
            )
        }
    }
}