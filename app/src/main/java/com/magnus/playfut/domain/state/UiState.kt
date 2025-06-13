package com.magnus.playfut.domain.state

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String? = null) : UiState<Nothing>()
}

fun UiState<*>.isLoading() = this is UiState.Loading
fun UiState<*>.isError() = this is UiState.Error
fun UiState<*>.isSuccess() = this is UiState.Success

fun <T> UiState<T>.asSuccess() = this as? UiState.Success