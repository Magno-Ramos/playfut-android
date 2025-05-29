package com.magnus.playfut.ui.features.rounds.sorting.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.extensions.setLightStatusBar
import com.magnus.playfut.ui.theme.AppTheme

class RoundSortFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setLightStatusBar()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                RoundSortFormScreen()
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, RoundSortFormActivity::class.java)
        }
    }
}