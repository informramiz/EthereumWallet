package com.ramiz.ethereumwallet.ui.screens

sealed class AppScreen(val route: String) {
    data object Home : AppScreen("Home")
    data object ImportWallet : AppScreen("ImportWallet")
    data object TransferEth : AppScreen("TransferEth")
    data object CreateWallet : AppScreen("CreateWallet")
}
