package com.magnus.playfut.ui.features.player.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.theme.AppTheme

class PlayerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val groupId = intent.getStringExtra(EXTRA_GROUP_ID) ?: return finish()
        setContent {
            AppTheme {
                PlayerListScreen(groupId = groupId)
            }
        }
    }

    companion object {
        const val EXTRA_GROUP_ID = "groupId"
        fun createIntent(context: Context, groupId: String): Intent {
            return Intent(context, PlayerListActivity::class.java).apply {
                putExtra(EXTRA_GROUP_ID, groupId)
            }
        }
    }
}