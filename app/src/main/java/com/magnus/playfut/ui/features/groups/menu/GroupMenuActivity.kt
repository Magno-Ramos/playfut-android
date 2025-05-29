package com.magnus.playfut.ui.features.groups.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.theme.AppTheme

class GroupMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val groupId = intent.getStringExtra(GROUP_ID) ?: return finish()

        setContent {
            AppTheme {
                GroupMenuScreen(
                    groupId = groupId,
                    onClickBack = { onBackPressedDispatcher.onBackPressed() }
                )
            }
        }
    }

    companion object {
        const val GROUP_ID = "group_id"
        fun createIntent(context: Context, groupId: String): Intent {
            return Intent(context, GroupMenuActivity::class.java).apply {
                putExtra(GROUP_ID, groupId)
            }
        }
    }
}