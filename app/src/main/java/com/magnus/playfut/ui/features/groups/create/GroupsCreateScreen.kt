package com.magnus.playfut.ui.features.groups.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.magnus.playfut.ui.domain.state.ActionResultState
import com.magnus.playfut.ui.features.groups.create.components.GroupsCreateForm
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsCreateScreen(
    viewModel: GroupsCreateViewModel = koinViewModel(),
    onClickBack: () -> Unit = {}
) {
    val createGroupResultState = viewModel.createGroupResult.collectAsState()

    LaunchedEffect(createGroupResultState) {
        when (createGroupResultState.value) {
            ActionResultState.Idle -> Unit
            ActionResultState.Error -> {}
            ActionResultState.Loading -> {}
            ActionResultState.Success -> onClickBack()
        }
    }

    AppTheme {
        Scaffold(
            containerColor = AppColor.bgPrimary,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AppColor.bgPrimary,
                    ),
                    title = {
                        Text(
                            text = "Criar Grupo",
                            fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onClickBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar"
                            )
                        }
                    }
                )
            }
        ) { paddings ->
            Box(modifier = Modifier.padding(paddings)) {
                GroupsCreateForm(
                    isLoading = createGroupResultState.value == ActionResultState.Loading,
                    onFormSubmit = { name ->
                        viewModel.createGroup(name)
                    }
                )
            }
        }
    }
}