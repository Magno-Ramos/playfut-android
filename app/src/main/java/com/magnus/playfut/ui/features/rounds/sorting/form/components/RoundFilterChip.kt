package com.magnus.playfut.ui.features.rounds.sorting.form.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun RoundFilterChip(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    leadingIcon: ImageVector? = null,
    onClickSelect: () -> Unit
) {

    val textIconColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val borderColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }

    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(FilterChipDefaults.shape)
            .clickable(enabled = true, role = Role.Checkbox) { onClickSelect() }
            .background(backgroundColor)
            .border(1.dp, borderColor, FilterChipDefaults.shape)
            .padding(MaterialTheme.spacing.extraSmall, MaterialTheme.spacing.small)
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = textIconColor,
                modifier = Modifier.size(FilterChipDefaults.IconSize)
            )

            Spacer(Modifier.width(MaterialTheme.spacing.small))
        }

        Text(
            text = label,
            color = textIconColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@PreviewLightDark
@Composable
private fun RoundFilterChipPreview() {
    AppTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.medium)
        ) {
            RoundFilterChip(
                label = "Random",
                selected = true,
                leadingIcon = Icons.Default.Shuffle,
                onClickSelect = {}
            )
            RoundFilterChip(
                label = "Random",
                selected = false,
                leadingIcon = Icons.Default.Shuffle,
                onClickSelect = {}
            )
        }
    }
}