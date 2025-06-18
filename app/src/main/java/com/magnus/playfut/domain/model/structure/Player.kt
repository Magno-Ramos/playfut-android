package com.magnus.playfut.domain.model.structure

import android.os.Parcelable
import com.magnus.playfut.domain.database.entities.structure.PlayerEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val id: String = "",
    val name: String = "",
    val skillLevel: Int = 0,
    val position: PlayerPosition = PlayerPosition.UNIVERSAL,
    val type: PlayerType = PlayerType.MEMBER,
    val groupId: String = "",
) : Parcelable

enum class PlayerType {
    MEMBER,
    GUEST
}

enum class PlayerPosition(val position: String, val acronym: String) {
    GOALKEEPER("Goleiro", "GOL"),
    DEFENDER("Zagueiro", "ZAG"),
    WINGER("Ala ou Lateral", "ALA"),
    MIDFIELDER("Meia", "MEI"),
    FORWARD("Atacante, Centroavante ou Pivô", "ATA"),
    UNIVERSAL("Jogador que faz várias funções", "UNI");

    fun isGoalkeeper() = this == GOALKEEPER

    fun isFieldPlayer() = this != GOALKEEPER

    companion object {
        fun fromType(type: String) = entries.first { it.position == type }
    }
}

fun PlayerEntity.toPlayer() = Player(
    id = playerId.toString(),
    groupId = groupOwnerId.toString(),
    name = name,
    skillLevel = skillLevel,
    type = type,
    position = position
)