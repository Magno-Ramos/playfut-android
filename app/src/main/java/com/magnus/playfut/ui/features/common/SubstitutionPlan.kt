package com.magnus.playfut.ui.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.domain.helper.Substitution
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

@Composable
fun SubstitutionPlan(groups: List<List<Substitution>>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(MaterialTheme.spacing.medium)
    ) {
        groups.forEach { group ->
            SubstitutionGroup(group)
        }
    }
}

@Composable
private fun SubstitutionGroup(group: List<Substitution>) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
    ) {
        group.forEach {
            SubstitutionItem(substitution = it)
        }
    }
}

@Composable
private fun SubstitutionItem(substitution: Substitution) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = null,
                tint = AppColor.green
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = substitution.inPlayer.name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        }
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.AutoMirrored.Filled.CompareArrows,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = substitution.outPlayer.name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = null,
                tint = AppColor.red
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun RoundTeamPreview() {
    AppTheme {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(24.dp)
        ) {
            SubstitutionItem(
                substitution = Substitution(
                    outPlayer = Player(name = "Jogador 1"),
                    inPlayer = Player(name = "Jogador 2")
                )
            )

            SubstitutionGroup(
                group = listOf(
                    Substitution(
                        outPlayer = Player(name = "Jogador 1"),
                        inPlayer = Player(name = "Jogador 2")
                    ),
                    Substitution(
                        outPlayer = Player(name = "Jogador 1"),
                        inPlayer = Player(name = "Jogador 2")
                    )
                )
            )
        }
    }
}