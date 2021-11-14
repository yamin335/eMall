package com.mallzhub.customer.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import com.mallzhub.customer.prefs.PreferencesHelper
import com.mallzhub.customer.ui.common.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {

}