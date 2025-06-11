package com.magnus.playfut.ui.domain.model.structure

import com.magnus.playfut.ui.domain.database.entities.structure.GroupEntity
import java.util.Date

class Group(
    val id: String = "",
    val name: String = "",
    val createdAt: Date = Date()
)

fun GroupEntity.toGroup() = Group(
    id = groupId.toString(),
    name = name,
    createdAt = createdAt
)