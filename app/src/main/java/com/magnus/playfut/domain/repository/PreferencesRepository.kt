package com.magnus.playfut.domain.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.magnus.playfut.domain.helper.DistributionType
import com.magnus.playfut.domain.repository.datasource.PreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepository(
    private val context: Context
) : PreferencesDataSource {
    val Context.playFutDataStore by preferencesDataStore(name = "play_fut_preferences")

    private val teamsCountKey = stringPreferencesKey("input_teams_count")
    private val playersCountKey = stringPreferencesKey("input_players_count")
    private val distributionTypeKey = stringPreferencesKey("input_distribution_type")

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

    override fun getDistributionType(): Flow<DistributionType> {
        return context.playFutDataStore.data.map { prefs ->
            val type = prefs[distributionTypeKey] ?: DistributionType.RANDOM.name
            DistributionType.valueOf(type)
        }
    }

    override fun getTeamsCount(): Flow<Int?> {
        return context.playFutDataStore.data.map { prefs ->
            val count = prefs[teamsCountKey]
            count?.toIntOrNull()
        }
    }

    override fun getPlayersCount(): Flow<Int?> {
        return context.playFutDataStore.data.map { prefs ->
            val count = prefs[playersCountKey]
            count?.toIntOrNull()
        }
    }
}