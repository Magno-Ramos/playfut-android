package com.magnus.playfut.domain.state

import kotlinx.coroutines.flow.MutableStateFlow

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val exception: Throwable? = null) : UiState<Nothing>()
}

fun UiState<*>.isLoading() = this is UiState.Loading
fun UiState<*>.isError() = this is UiState.Error
fun UiState<*>.isSuccess() = this is UiState.Success

fun <T> UiState<T>.asSuccess() = this as? UiState.Success
fun <T> UiState<T>.asError() = this as? UiState.Error

fun <T> Result<T>.collectUiState(state: MutableStateFlow<UiState<T>>) {
    state.value = UiState.Loading
    onFailure {
        print(it)
        state.value = UiState.Error(it)
    }
    onSuccess { state.value = UiState.Success(it) }
}