package com.magnus.playfut.ui.features.statistic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.features.statistic.home.StatisticHomeScreen
import com.magnus.playfut.ui.theme.AppTheme

class StatisticActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val groupId = intent.getStringExtra(GROUP_ID) ?: return finish()
        setContent {
            AppTheme {
                StatisticHomeScreen(groupId = groupId)
            }
        }
    }

    companion object {
        private const val GROUP_ID = "group_id"
        fun createIntent(context: Context, groupId: String) = Intent(context, StatisticActivity::class.java).apply {
            putExtra(GROUP_ID, groupId)
        }
    }
}