package com.magnus.playfut.ui.features.rounds.sorting.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.theme.AppTheme

class RoundSortActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val groupId = intent.getStringExtra(EXTRA_GROUP_ID) ?: return finish()

        setContent {
            AppTheme {
                RoundSortScreen(groupId = groupId)
            }
        }
    }

    companion object {
        private const val EXTRA_GROUP_ID = "EXTRA_GROUP_ID"
        fun createIntent(context: Context, groupId: String): Intent {
            return Intent(context, RoundSortActivity::class.java).apply {
                putExtra(EXTRA_GROUP_ID, groupId)
            }
        }
    }
}