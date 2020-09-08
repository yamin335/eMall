package com.qpay.customer.ui.topup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.qpay.customer.ui.common.BaseViewModel
import javax.inject.Inject

class TopUpAmountViewModel @Inject constructor(private val application: Application) : BaseViewModel(application) {

    val amount: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}