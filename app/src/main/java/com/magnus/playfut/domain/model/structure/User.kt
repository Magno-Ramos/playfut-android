package com.magnus.playfut.domain.model.structure

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class User(
    val uid: String = "",
    @PropertyName("pro")
    val isPro: Boolean = false,
    val firstAccess: Long = Date().time
)