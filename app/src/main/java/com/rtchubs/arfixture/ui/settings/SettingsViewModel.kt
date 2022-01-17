package com.rtchubs.arfixture.ui.settings

import android.app.Application
import com.rtchubs.arfixture.ui.common.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {

}