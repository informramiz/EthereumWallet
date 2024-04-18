package com.ramiz.ethereumwallet.presentation.home

import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import com.ramiz.ethereumwallet.ui.screens.importwallet.ImportWalletNavigationDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ScreenBaseViewModel<HomeViewState>() {
    override fun defaultViewState() = HomeViewState()

    fun onAddExistingWalletAction() {
        navigate(ImportWalletNavigationDestination)
    }
}
