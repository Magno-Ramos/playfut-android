package com.magnus.playfut.ui.domain.model

import android.content.Context
import android.os.Parcelable
import com.magnus.playfut.R
import kotlinx.parcelize.Parcelize
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

@Parcelize
class Player(
    val id: String = "",
    val name: String = "",
    val skillLevel: Int = 0,
    val type: PlayerType = PlayerType.UNIVERSAL,
    val groupId: String = "",
) : Parcelable

fun List<Round>.toRealizedRoundsString(context: Context): String {
    return if (isEmpty()) {
        context.getString(R.string.no_rounds_realized)
    } else {
        context.resources.getQuantityString(R.plurals.rounds_realized, size, size)
    }
}

fun List<Player>.toPlayersCountString(context: Context): String {
    return if (isEmpty()) {
        context.getString(R.string.no_players)
    } else {
        context.resources.getQuantityString(R.plurals.players_count, size, size)
    }
}

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