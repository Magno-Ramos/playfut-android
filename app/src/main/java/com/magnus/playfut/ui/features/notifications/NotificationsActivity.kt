package com.magnus.playfut.ui.features.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.theme.AppTheme
import com.magnus.playfut.ui.theme.spacing

class NotificationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                NotificationsScreen()
            }
        }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, NotificationsActivity::class.java)
    }
}

@Composable
private fun NotificationsScreen() {
    val context = LocalContext.current

    fun finish() {
        context.activity?.finish()
    }

    Scaffold(
        topBar = {
            AppToolbar(title = "Notificações", onClickBack = ::finish)
        }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings).fillMaxSize()) {
            Text(
                textAlign = TextAlign.Center,
                text = "Nenhuma notificação até o momento.",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .align(Alignment.Center),
            )
        }
    }
}