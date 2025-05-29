package com.magnus.playfut.ui.features.rounds.sorting.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.extensions.getParcelableArrayListExtraCompat
import com.magnus.playfut.ui.features.rounds.sorting.form.model.SelectablePlayer
import com.magnus.playfut.ui.theme.AppTheme

class RoundSortPlayerSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val selectedPlayers: ArrayList<SelectablePlayer> =
            intent.getParcelableArrayListExtraCompat(EXTRA_PLAYERS_LIST) ?: return finish()

        setContent {
            AppTheme {
                RoundSortPlayerSelectionScreen(
                    selectedPlayers = selectedPlayers.toList()
                )
            }
        }
    }

    companion object {
        const val EXTRA_PLAYERS_LIST = "EXTRA_PLAYERS_LIST"
        fun createIntent(context: Context, playersList: List<SelectablePlayer>): Intent {
            val list = arrayListOf<SelectablePlayer>()
            list.addAll(playersList)
            return Intent(context, RoundSortPlayerSelectionActivity::class.java).apply {
                putExtra(EXTRA_PLAYERS_LIST, list)
            }
        }
    }
}