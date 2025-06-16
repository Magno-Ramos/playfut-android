package com.magnus.playfut.domain.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.magnus.playfut.domain.helper.DistributionType
import com.magnus.playfut.domain.repository.datasource.PreferencesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreferencesRepository(
    private val context: Context
) : PreferencesDataSource {
    val Context.playFutDataStore by preferencesDataStore(name = "play_fut_preferences")

    private val teamsCountKey = stringPreferencesKey("input_teams_count")
    private val playersCountKey = stringPreferencesKey("input_players_count")
    private val distributionTypeKey = stringPreferencesKey("input_distribution_type")

    val scope = CoroutineScope(Dispatchers.IO)

    var teamsCount = 2
        private set

    var playersCount = 5
        private set

    var distributionType = DistributionType.RANDOM
        private set

    init {
        scope.launch {
            context.playFutDataStore.data.collect { prefs ->
                teamsCount = prefs[teamsCountKey]?.toIntOrNull() ?: 2
                playersCount = prefs[playersCountKey]?.toIntOrNull() ?: 5
                distributionType = DistributionType.entries.find {
                    it.name == prefs[distributionTypeKey]
                } ?: DistributionType.RANDOM
            }
        }
    }

    override suspend fun saveDistributionType(type: DistributionType) {
        context.playFutDataStore.edit { prefs ->
            prefs[distributionTypeKey] = type.name
        }
    }

    override suspend fun saveInputTeamsCount(count: Int?) {
        context.playFutDataStore.edit { prefs ->
            prefs[teamsCountKey] = count?.toString() ?: ""
        }
    }

    override suspend fun saveInputPlayersCount(count: Int?) {
        context.playFutDataStore.edit { prefs ->
            prefs[playersCountKey] = count?.toString() ?: ""
        }
    }
}