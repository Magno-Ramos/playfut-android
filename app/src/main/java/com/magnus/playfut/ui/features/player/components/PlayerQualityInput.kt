package com.magnus.playfut.ui.features.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnus.playfut.ui.theme.AppColor
import com.magnus.playfut.ui.theme.AppTheme
import kotlin.math.roundToInt

@Composable
fun PlayerQualityInput(
    quality: Int = 3,
    onQualityChange: (Int) -> Unit = {}
) {
    Column {
        Text(
            text = "Pontuação de habilidade",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = AppColor.secondaryText
        )
        Spacer(Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(AppColor.white, RoundedCornerShape(8.dp))
                .padding(16.dp, 8.dp)
        ) {
            Slider(
                modifier = Modifier.weight(1f),
                value = quality.toFloat(),
                onValueChange = { onQualityChange(it.roundToInt()) },
                valueRange = 1f..5f,
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = AppColor.primary,
                    activeTrackColor = AppColor.primary,
                    inactiveTrackColor = AppColor.bgPrimary
                )
            )
            Text(
                text = quality.toString(),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun PlayerQualityInputPreview() {
    AppTheme {
        Column(
            Modifier
                .background(AppColor.bgPrimary)
                .padding(16.dp)
        ) {
            PlayerQualityInput()
        }
    }
}