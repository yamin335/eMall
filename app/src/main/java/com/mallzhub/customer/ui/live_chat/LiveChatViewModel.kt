package com.mallzhub.customer.ui.live_chat

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.mallzhub.customer.local_db.dao.CartDao
import com.mallzhub.customer.models.LiveChatMessage
import com.mallzhub.customer.ui.common.BaseViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LiveChatViewModel @Inject constructor(
    private val application: Application,
    private val cartDao: CartDao
    ) : BaseViewModel(application) {

    val newMessage: MutableLiveData<String> = MutableLiveData()

    val chatMessages: MutableLiveData<MutableList<LiveChatMessage>> by lazy {
        MutableLiveData<MutableList<LiveChatMessage>>()
    }

}