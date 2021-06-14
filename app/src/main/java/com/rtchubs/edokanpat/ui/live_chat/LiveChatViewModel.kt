package com.rtchubs.edokanpat.ui.live_chat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rtchubs.edokanpat.prefs.PreferencesHelper
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import javax.inject.Inject

class LiveChatViewModel @Inject constructor(private val application: Application) : BaseViewModel(application) {

    val newMessage: MutableLiveData<String> = MutableLiveData()

}