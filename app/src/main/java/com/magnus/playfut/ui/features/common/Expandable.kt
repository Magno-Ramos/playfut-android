package com.magnus.playfut.ui.features.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun Expandable(
    title: String,
    initExpanded: Boolean = false,
    onExpand: () -> Unit = {},
    onCollapse: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var expanded = remember { mutableStateOf(initExpanded) }

    fun toggleExpand() {
        expanded.value = !expanded.value
        if (expanded.value) onExpand() else onCollapse()
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .clickable(role = Role.Button) { toggleExpand() }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        AnimatedVisibility(visible = expanded.value) {
            content()
        }
    }
}

@PreviewLightDark
@Composable
private fun ExpandablePreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            Expandable(
                initExpanded = true,
                title = "That is a test"
            ) {
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = "That is the content"
                )
            }
        }
    }
}