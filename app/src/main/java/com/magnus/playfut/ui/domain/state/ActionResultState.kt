package com.magnus.playfut.ui.domain.state

sealed class ActionResultState <out T> {
    object Idle : ActionResultState<Nothing>()
    object Loading : ActionResultState<Nothing>()
    data class Error(val message: String? = null) : ActionResultState<Nothing>()
    data class Success<T>(val data: T) : ActionResultState<T>()
}