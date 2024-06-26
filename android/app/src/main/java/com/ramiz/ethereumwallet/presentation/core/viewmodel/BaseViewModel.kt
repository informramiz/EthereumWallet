package com.ramiz.ethereumwallet.presentation.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<VIEW_STATE: ViewState> : ViewModel() {
    private val _viewState by lazy { MutableStateFlow(defaultViewState()) }
    val viewState = _viewState.asStateFlow()

    fun updateState(block: (VIEW_STATE) -> VIEW_STATE) {
        _viewState.value = block(currentViewState())
    }

    fun currentViewState() = viewState.value

    abstract fun defaultViewState(): VIEW_STATE

    @Suppress("detekt:TooGenericExceptionCaught")
    protected open fun launchWithErrorHandling(
        onError: (Exception) -> Unit = {},
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
