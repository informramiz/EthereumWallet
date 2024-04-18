package com.ramiz.ethereumwallet.presentation.importwallet

import com.ramiz.ethereumwallet.presentation.core.viewmodel.ScreenBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImportWalletViewModel @Inject constructor() : ScreenBaseViewModel<ImportWalletViewState>() {
    override fun defaultViewState() = ImportWalletViewState()
}
