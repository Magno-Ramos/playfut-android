package com.magnus.playfut.ui.domain.model

import java.util.Date

class Group(
    val id: String = "",
    val name: String = "",
    val players: List<Player> = listOf(),
    val rounds: List<Round> = listOf()
)

class Round(
    val winner: String = "",
    val date: Date = Date()
)

class Player(
    val name: String = "",
    val quality: Int = 0,
    val type: PlayerType = PlayerType.UNIVERSAL
)

enum class PlayerType(val type: String) {
    GOALKEEPER("Goleiro"),
    DEFENDER("Zagueiro"),
    WINGER("Ala ou Lateral"),
    MIDFIELDER("Meia"),
    FORWARD("Atacante, Pivô ou Centroavante"),
    UNIVERSAL("Jogador que faz várias funções");

    companion object {
        fun fromType(type: String) = entries.first { it.type == type }
    }
}