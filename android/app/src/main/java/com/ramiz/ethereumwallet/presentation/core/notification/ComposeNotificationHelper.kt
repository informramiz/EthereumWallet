package com.ramiz.ethereumwallet.presentation.core.notification

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

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
