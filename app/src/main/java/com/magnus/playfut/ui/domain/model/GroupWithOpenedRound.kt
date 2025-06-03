package com.magnus.playfut.ui.domain.model

data class GroupWithOpenedRound(
    val group: Group,
    val runningRound: Round?
)