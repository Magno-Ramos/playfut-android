package com.magnus.playfut.ui.features.groups.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.theme.AppTheme

class GroupsFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val groupId = intent.getStringExtra(GROUP_ID_KEY)
        val groupName = intent.getStringExtra(GROUP_NAME_KEY).orEmpty()

        setContent {
            AppTheme {
                if (groupId.isNullOrBlank()) {
                    GroupsCreateScreen()
                } else {
                    GroupsEditScreen(groupId = groupId, groupName = groupName)
                }
            }
        }
    }

    companion object {
        private const val GROUP_ID_KEY = "groupId"
        private const val GROUP_NAME_KEY = "groupName"

        fun createIntentToEdit(context: Context, groupId: String, groupName: String) =
            Intent(context, GroupsFormActivity::class.java).apply {
                putExtra(GROUP_ID_KEY, groupId)
                putExtra(GROUP_NAME_KEY, groupName)
            }

        fun createIntent(context: Context) =
            Intent(context, GroupsFormActivity::class.java)
    }
}