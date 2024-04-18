package com.ramiz.ethereumwallet.presentation.core.viewmodel

import com.ramiz.ethereumwallet.presentation.core.navigation.GeneralNavigationDestination
import com.ramiz.ethereumwallet.presentation.core.navigation.NavigationDestination
import com.ramiz.ethereumwallet.presentation.core.notification.NotificationCommand
import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeoutException

abstract class ScreenBaseViewModel<VIEW_STATE : ViewState> : BaseViewModel<VIEW_STATE>() {

    private val _navigationCommand = MutableStateFlow<NavigationDestination?>(null)
    val navigationCommand = _navigationCommand.asStateFlow()

    private val _notificationCommand = MutableStateFlow<NotificationCommand?>(null)
    val notificationCommand = _notificationCommand.asStateFlow()

    fun navigate(destination: NavigationDestination) {
        _navigationCommand.value = destination
    }

    fun navigateBack() {
        _navigationCommand.value = GeneralNavigationDestination.Back
    }

    fun onNavigationDone() {
        _navigationCommand.value = null
    }

    fun notify(notificationCommand: NotificationCommand) {
        _notificationCommand.value = notificationCommand
    }

    fun notify(message: String) {
        _notificationCommand.value = NotificationCommand.Normal(message)
    }

    fun notifyError(message: String?) {
        _notificationCommand.value = NotificationCommand.Error(message)
    }

    fun onNotifyDone() {
        _notificationCommand.value = null
        _navigationCommand.value = null
    }

    protected fun launchWithDefaultErrorHandling(
        onEndWithError: () -> Unit = {},
        block: suspend () -> Unit
    ) {
        launchWithErrorHandling(
            onError = {
                onEndWithError()
                notifyError(it.message)
                Timber.e("onEndWithError ${parseException(it)}")
            },
            block = block
        )
    }

    protected fun <T> Flow<T>.catchWithDefaultErrorHandler(doOnError: () -> Unit = {}): Flow<T> {
        return catch { cause: Throwable ->
            val message = parseException(cause)
            doOnError()
            notifyError(message)
            Timber.e(message)
        }
    }

    private fun parseException(exception: Throwable): String? {
        return when (exception) {
            is java.io.InterruptedIOException,
            is java.net.UnknownHostException,
            is javax.net.ssl.SSLException,
            is java.net.NoRouteToHostException,
            is java.net.SocketException -> exception.message
            is IOException, is TimeoutException -> exception.message
            else -> exception.message
        }
    }
}
