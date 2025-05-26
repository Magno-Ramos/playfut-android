package com.magnus.playfut.ui.domain.repository

import com.magnus.playfut.ui.domain.model.Group

class GroupRepository {
    fun getGroups(): List<Group> {
        return listOf(
            Group("1", "Group A", listOf("Player 1", "Player 2", "Player 3"), rounds = 13),
            Group("1", "Group A", listOf("Player 1", "Player 2", "Player 3"), rounds = 13),
        )
    }
}