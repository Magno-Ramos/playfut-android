package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.helper.DistributionType

interface PreferencesDataSource {
    suspend fun saveDistributionType(type: DistributionType)
    suspend fun saveInputTeamsCount(count: Int?)
    suspend  fun saveInputPlayersCount(count: Int?)
}