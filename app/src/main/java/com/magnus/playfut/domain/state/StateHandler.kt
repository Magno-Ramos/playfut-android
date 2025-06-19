package com.magnus.playfut.domain.state

import androidx.compose.runtime.Composable

@Composable
fun <T> StateHandler(state: ActionResultState<T>, builder: ActionResultScope<T>.() -> Unit) {
    val scope = ActionResultScope<T>().apply(builder)
    when (state) {
        ActionResultState.Idle -> scope.idleContent()
        ActionResultState.Loading -> scope.loadingContent()
        is ActionResultState.Error -> scope.errorContent(state.message)
        is ActionResultState.Success<T> -> scope.successContent(state.data)
    }
}

@Composable
fun <T> StateHandler(state: UiState<T>, builder: UiHandlerScope<T>.() -> Unit) {
    val scope = UiHandlerScope<T>().apply(builder)
    when (state) {
        is UiState.Loading -> scope.loadingContent()
        is UiState.Success -> scope.successContent(state.data)
        is UiState.Error -> scope.errorContent(state.exception)
    }
}

class ActionResultScope<T> {
    var idleContent: @Composable () -> Unit = {}
    var loadingContent: @Composable () -> Unit = {}
    var successContent: @Composable (T) -> Unit = {}
    var errorContent: @Composable (String?) -> Unit = {}

    fun idle(content: @Composable () -> Unit) {
        idleContent = content
    }

    fun loading(content: @Composable () -> Unit) {
        loadingContent = content
    }

    fun success(content: @Composable (T) -> Unit) {
        successContent = content
    }

    fun error(content: @Composable (String?) -> Unit) {
        errorContent = content
    }
}

class UiHandlerScope<T> {
    var loadingContent: @Composable () -> Unit = {}
    var successContent: @Composable (T) -> Unit = {}
    var errorContent: @Composable (Throwable?) -> Unit = {}

    fun loading(content: @Composable () -> Unit) {
        loadingContent = content
    }

    fun success(content: @Composable (T) -> Unit) {
        successContent = content
    }

    fun error(content: @Composable (Throwable?) -> Unit) {
        errorContent = content
    }
}
