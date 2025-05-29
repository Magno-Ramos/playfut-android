package com.magnus.playfut.ui.features.groups.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.theme.AppTheme

class GroupSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val groupId = intent.getStringExtra("group_id") ?: return finish()

        setContent {
            AppTheme {
                GroupSettingsScreen(groupId = groupId) {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

    companion object {
        fun createIntent(context: Context, groupId: String): Intent {
            return Intent(context, GroupSettingsActivity::class.java).apply {
                putExtra("group_id", groupId)
            }
        }
    }
}
