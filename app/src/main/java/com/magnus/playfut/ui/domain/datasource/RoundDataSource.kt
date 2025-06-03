package com.magnus.playfut.ui.domain.datasource

import com.magnus.playfut.ui.domain.model.Round

interface RoundDataSource {

    suspend fun fetchRunningRound(groupId: String): Result<Round?>

    suspend fun fetchAllRounds(groupId: String): Result<List<Round>>
}