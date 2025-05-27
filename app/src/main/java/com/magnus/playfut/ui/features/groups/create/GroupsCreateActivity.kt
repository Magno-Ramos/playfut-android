package com.magnus.playfut.ui.features.groups.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.extensions.setLightStatusBar
import com.magnus.playfut.ui.theme.AppTheme

class GroupsCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setLightStatusBar()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                GroupsCreateScreen(
                    onClickBack = { onBackPressedDispatcher.onBackPressed() }
                )
            }
        }
    }

    companion object {
        fun createIntent(context: Context) =
            Intent(context, GroupsCreateActivity::class.java)
    }
}