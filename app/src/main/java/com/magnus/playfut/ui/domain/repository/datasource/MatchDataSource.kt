package com.magnus.playfut.ui.domain.repository.datasource

import com.magnus.playfut.ui.domain.model.form.MatchForm
import com.magnus.playfut.ui.domain.model.structure.Match

interface MatchDataSource {
    suspend fun getMatchesFromRound(roundId: String): Result<List<Match>>

    suspend fun pushMatch(form: MatchForm): Result<Long>
}