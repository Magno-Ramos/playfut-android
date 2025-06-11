package com.magnus.playfut.ui.features.groups.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magnus.playfut.domain.state.ActionResultState
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.groups.settings.components.DeleteBottomSheet
import com.magnus.playfut.ui.features.groups.settings.components.DeleteButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GroupSettingsScreen(
    viewModel: GroupSettingsViewModel = koinViewModel(),
    groupId: String,
    onClickBack: () -> Unit = {}
) {
    val deleteGroupResultState = viewModel.deleteGroupResult.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    fun confirmDelete() {
        scope.launch {
            viewModel.deleteGroup(groupId)
            sheetState.hide()
        }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet = false
            }
        }
    }

    LaunchedEffect(deleteGroupResultState.value) {
        when (deleteGroupResultState.value) {
            ActionResultState.Idle -> Unit
            ActionResultState.Loading -> Unit
            is ActionResultState.Success -> onClickBack()
            is ActionResultState.Error -> {
                snackBarHostState.showSnackbar(message = "Erro ao excluir o grupo")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { AppToolbar(title = "Configurações", onClickBack = onClickBack) }
    ) { paddings ->
        Box {
            Column(
                modifier = Modifier
                    .padding(paddings)
                    .padding(16.dp)
            ) {
                DeleteButton(
                    isLoading = deleteGroupResultState.value == ActionResultState.Loading,
                    onClick = { showBottomSheet = true }
                )
            }

            if (showBottomSheet) {
                DeleteBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    onConfirmDelete = { confirmDelete() }
                )
            }
        }
    }
}
