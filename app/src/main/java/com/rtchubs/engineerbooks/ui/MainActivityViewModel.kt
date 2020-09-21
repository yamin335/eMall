package com.rtchubs.engineerbooks.ui

import android.app.Application
import com.rtchubs.engineerbooks.ui.common.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val application: Application) : BaseViewModel(application) {

}