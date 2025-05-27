package com.magnus.playfut.ui.domain.state

sealed class ActionResultState {
    object Idle : ActionResultState()
    object Loading : ActionResultState()
    object Success : ActionResultState()
    object Error : ActionResultState()
}