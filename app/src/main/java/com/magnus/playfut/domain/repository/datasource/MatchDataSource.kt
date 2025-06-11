package com.magnus.playfut.domain.repository.datasource

import com.magnus.playfut.domain.model.form.MatchForm
import com.magnus.playfut.domain.model.structure.Match

interface MatchDataSource {
    suspend fun getMatchesFromRound(roundId: String): Result<List<Match>>

    suspend fun pushMatch(form: MatchForm): Result<Long>
}