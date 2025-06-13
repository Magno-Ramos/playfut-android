package com.magnus.playfut.ui.features.rounds.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.theme.AppTheme

class RoundHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val groupId = intent.getStringExtra(GROUP_ID) ?: return finish()

        setContent {
            AppTheme {
                RoundHistoryScreen(groupId = groupId)
            }
        }
    }

    companion object {
        const val GROUP_ID = "groupId"
        fun createIntent(context: Context, groupId: String): Intent {
            return Intent(context, RoundHistoryActivity::class.java).apply {
                putExtra(GROUP_ID, groupId)
            }
        }
    }
}