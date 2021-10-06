package com.mallzhub.customer.ui.live_chat

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mallzhub.customer.ui.common.BaseViewModel
import javax.inject.Inject

class LiveChatViewModel @Inject constructor(private val application: Application) : BaseViewModel(application) {

    val newMessage: MutableLiveData<String> = MutableLiveData()

}