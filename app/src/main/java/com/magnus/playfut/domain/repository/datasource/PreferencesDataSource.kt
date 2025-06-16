package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.helper.DistributionType
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    suspend fun saveDistributionType(type: DistributionType)
    suspend fun saveInputTeamsCount(count: Int?)
    suspend  fun saveInputPlayersCount(count: Int?)

    fun getDistributionType(): Flow<DistributionType>
    fun getTeamsCount(): Flow<Int?>
    fun getPlayersCount(): Flow<Int?>
}