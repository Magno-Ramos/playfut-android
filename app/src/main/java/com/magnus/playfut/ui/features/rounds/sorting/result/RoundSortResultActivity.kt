package com.magnus.playfut.ui.features.rounds.sorting.result

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.extensions.setLightStatusBar
import com.magnus.playfut.ui.theme.AppTheme

class RoundSortResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setLightStatusBar()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                RoundSortResultScreen()
            }
        }
    }
}