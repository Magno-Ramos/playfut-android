package com.magnus.playfut.ui.features.player.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.magnus.playfut.ui.domain.model.structure.Player
import com.magnus.playfut.ui.extensions.getParcelableExtraCompat
import com.magnus.playfut.ui.theme.AppTheme

class PlayerFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val groupId = intent.getStringExtra(EXTRA_GROUP_ID)
        val player = intent.getParcelableExtraCompat<Player>(EXTRA_PLAYER)

        title = when {
            player != null -> "Editar jogador"
            groupId != null -> "Adicionar jogador"
            else -> ""
        }

        setContent {
            AppTheme {
                when {
                    player != null -> PLayerEditScreen(player = player)
                    groupId != null -> PlayerCreateScreen(groupId = groupId)
                    else -> finish()
                }
            }
        }
    }

    companion object {
        private const val EXTRA_GROUP_ID = "EXTRA_GROUP_ID"
        private const val EXTRA_PLAYER = "EXTRA_PLAYER"

        fun createIntentToEdit(context: Context, player: Player): Intent {
            return Intent(context, PlayerFormActivity::class.java).apply {
                putExtra(EXTRA_PLAYER, player)
            }
        }

        fun createIntentToCreate(context: Context, groupId: String): Intent {
            return Intent(context, PlayerFormActivity::class.java).apply {
                putExtra(EXTRA_GROUP_ID, groupId)
            }
        }
    }
}