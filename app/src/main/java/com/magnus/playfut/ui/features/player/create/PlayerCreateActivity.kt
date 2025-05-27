package com.magnus.playfut.ui.features.player.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.extensions.setLightStatusBar
import com.magnus.playfut.ui.theme.AppTheme

class PlayerCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setLightStatusBar()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PlayerCreateScreen()
            }
        }
    }

    companion object {
        private const val EXTRA_GROUP_ID = "EXTRA_GROUP_ID"
        fun createIntent(context: Context, groupId: String): Intent {
            return Intent(context, PlayerCreateActivity::class.java).apply {
                putExtra(EXTRA_GROUP_ID, groupId)
            }
        }
    }
}