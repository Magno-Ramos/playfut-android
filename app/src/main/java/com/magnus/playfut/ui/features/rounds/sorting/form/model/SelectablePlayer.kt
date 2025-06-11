package com.magnus.playfut.ui.features.rounds.sorting.form.model

import android.os.Parcelable
import com.magnus.playfut.domain.model.structure.Player
import com.magnus.playfut.domain.model.structure.PlayerType
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectablePlayer(
    val id: String = "",
    val name: String = "",
    val skillLevel: Int = 0,
    val type: PlayerType = PlayerType.UNIVERSAL,
    val groupId: String = "",
    var selected: Boolean = true
) : Parcelable

fun Player.toSelectablePlayer() = SelectablePlayer(
    id = id,
    name = name,
    skillLevel = skillLevel,
    type = type,
    groupId = groupId,
    selected = true
)

fun SelectablePlayer.toPlayer() = Player(
    id = id,
    name = name,
    skillLevel = skillLevel,
    type = type,
    groupId = groupId
)