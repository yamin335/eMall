package com.rtchubs.edokanpat.ui.setup_complete

import android.content.Context
import androidx.lifecycle.ViewModel
import com.rtchubs.edokanpat.prefs.PreferencesHelper
import javax.inject.Inject

class SetupCompleteViewModel @Inject constructor(
    private val preferencesHelper: PreferencesHelper,
    private val context: Context
) : ViewModel() {

}