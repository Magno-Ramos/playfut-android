package com.magnus.playfut.ui.features.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.magnus.playfut.ui.features.common.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBarContent(
    modifier: Modifier = Modifier,
    title: String = "",
    onClickNotification: () -> Unit = {},
    onClickCreate: () -> Unit = {}
) {
    AppToolbar(
        modifier = modifier,
        title = title,
        actions = {
            IconButton(onClick = onClickCreate) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null
                )
            }
            IconButton(onClick = onClickNotification) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null
                )
            }
        }
    )
}