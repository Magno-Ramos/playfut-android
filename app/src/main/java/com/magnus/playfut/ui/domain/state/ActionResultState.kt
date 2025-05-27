package com.magnus.playfut.ui.domain.state

sealed class ActionResultState {
    object Idle : ActionResultState()
    object Loading : ActionResultState()
    object Success : ActionResultState()
    data class Error(val message: String? = null) : ActionResultState()
}