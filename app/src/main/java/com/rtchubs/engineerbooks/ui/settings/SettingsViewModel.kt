package com.rtchubs.engineerbooks.ui.settings

import androidx.lifecycle.ViewModel
import com.rtchubs.engineerbooks.prefs.PreferencesHelper
import javax.inject.Inject

class SettingsViewModel  @Inject constructor(private val preferencesHelper: PreferencesHelper) : ViewModel() {
}