package com.magnus.playfut.ui.features.groups.form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.features.groups.components.GroupForm
import com.magnus.playfut.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsEditScreen(
    viewModel: GroupsFormViewModel = koinViewModel(),
    groupId: String,
    groupName: String,
) {
    val context = LocalContext.current
    val editGroupResultState = viewModel.editGroupResult.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val name = remember { mutableStateOf(groupName) }

    fun closeScreen() {
        context.activity?.finish()
    }

    fun showSnackBar(message: String) {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message = message)
        }
    }

    when (editGroupResultState.value) {
        ActionResultState.Idle -> Unit
        ActionResultState.Loading -> Unit
        is ActionResultState.Success<Unit> -> { closeScreen() }
        is ActionResultState.Error -> showSnackBar("Erro ao criar grupo")
    }

    val isButtonEnabled = (editGroupResultState.value != ActionResultState.Loading && name.value.isNotBlank())

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            topBar = {
                AppToolbar(title = "Editar Grupo", onClickBack = { closeScreen() })
            },
            bottomBar = {
                AppBottomBar(
                    isButtonEnabled = isButtonEnabled,
                    onClickSaveGroup = { viewModel.editGroup(groupId, name.value) }
                )
            }
        ) { paddings ->
            Box(modifier = Modifier.padding(paddings)) {
                GroupForm(
                    name = name.value,
                    onNameChange = { name.value = it },
                    requestFocus = false
                )
            }
        }
    }
}


@Composable
private fun AppBottomBar(
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean,
    onClickSaveGroup: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            enabled = isButtonEnabled,
            onClick = { onClickSaveGroup() }
        ) {
            Text(text = "Salvar")
        }
    }
}