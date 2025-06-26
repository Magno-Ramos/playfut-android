package com.magnus.playfut.domain.model.structure

interface User {
    val uid: String
}

data class UserAnonymously(
    override val uid: String = "",
    val isPro: Boolean = false
) : User

data class UserRegistered(
    override val uid: String,
    val isPro: Boolean,
    val displayName: String
) : User