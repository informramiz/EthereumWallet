package com.ramiz.ethereumwallet.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramiz.ethereumwallet.presentation.home.HomeViewModel
import com.ramiz.ethereumwallet.ui.components.AppScaffold

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    AppScaffold(title = "Ethereum Wallet") {
        Text(text = "Hello World")
    }
}
