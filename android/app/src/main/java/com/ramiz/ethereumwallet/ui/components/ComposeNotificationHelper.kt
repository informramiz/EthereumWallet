package com.ramiz.ethereumwallet.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.ramiz.ethereumwallet.presentation.core.notification.NotificationCommand
import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import com.ramiz.ethereumwallet.presentation.core.viewstate.ViewState

@Composable
fun ScreenNotification(
    snackbarHostState: SnackbarHostState,
    notificationCommand: NotificationCommand?,
    onNotifyDone: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = notificationCommand) {
        notificationCommand?.let { snackbarHostState.showSnackbar(it.resolveMessage(context)) }
        onNotifyDone()
    }
}

@Composable
fun <V: ViewState> ScreenNotification(
    viewModel: ScreenBaseViewModel<V>,
    snackbarHostState: SnackbarHostState,
    notificationCommand: NotificationCommand?
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = notificationCommand) {
        notificationCommand?.let { snackbarHostState.showSnackbar(it.resolveMessage(context)) }
        viewModel.onNotifyDone()
    }
}
