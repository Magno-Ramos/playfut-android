package com.magnus.playfut.domain.model.ui

import com.magnus.playfut.domain.model.structure.Artillery
import com.magnus.playfut.domain.model.structure.Player

class RoundPlayingUiModel (
    val round: RoundDetailsUiModel,
    val ranking: List<Artillery>,
    val players: List<Player>
)