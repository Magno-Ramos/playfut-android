package com.magnus.playfut.ui.features.statistic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.magnus.playfut.extensions.activity
import com.magnus.playfut.ui.features.common.AppToolbar
import com.magnus.playfut.ui.theme.AppTheme

class StatisticHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                StatisticHomeScreen()
            }
        }
    }

    companion object {
        fun createIntent(context: Context) =
            Intent(context, StatisticHomeActivity::class.java)
    }
}

@Composable
private fun StatisticHomeScreen() {
    val context = LocalContext.current

    fun finish() {
        context.activity?.finish()
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Estatísticas",
                onClickBack = { finish() }
            )
        }
    ) { paddings ->
        Box(Modifier.padding(paddings)) {

        }
    }
}