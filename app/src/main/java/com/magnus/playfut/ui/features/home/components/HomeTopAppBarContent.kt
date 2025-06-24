package com.magnus.playfut.ui.features.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.magnus.playfut.ui.features.common.AppToolbar

@Composable
fun HomeTopAppBarContent(
    modifier: Modifier = Modifier,
    title: String = "",
    onClickNotification: () -> Unit = {}
) {
    AppToolbar(
        modifier = modifier,
        title = title,
        actions = {
            IconButton(onClick = onClickNotification) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null
                )
            }
        }
    )
}