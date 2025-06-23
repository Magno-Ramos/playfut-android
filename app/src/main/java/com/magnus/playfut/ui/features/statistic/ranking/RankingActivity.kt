package com.magnus.playfut.ui.features.statistic.ranking

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

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val groupId = intent.getStringExtra(GROUP_ID) ?: return finish()
        // val rankingType = intent.getSerializableExtraCompat<RankingType>(RANKING_TYPE) ?: finish()
        setContent {
            AppTheme {
                RankingApp(groupId)
            }
        }
    }

    companion object {
        private const val GROUP_ID = "group_id"
        private const val RANKING_TYPE = "ranking_type"
        fun createIntent(context: Context, groupId: String, rankingType: RankingType): Intent {
            return Intent(context, RankingActivity::class.java).apply {
                putExtra(GROUP_ID, groupId)
                putExtra(RANKING_TYPE, rankingType)
            }
        }
    }
}

@Composable
private fun RankingApp(groupId: String) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Ranking de Artilharia",
                onClickBack = { context.activity?.finish() }
            )
        }
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            ArtilleryRankingScreen(groupId = groupId)
        }
    }
}