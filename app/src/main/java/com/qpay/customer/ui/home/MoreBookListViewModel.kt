package com.qpay.customer.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.qpay.customer.api.*
import com.qpay.customer.models.payment_account_models.AddCardOrBankResponse
import com.qpay.customer.models.registration.DefaultResponse
import com.qpay.customer.prefs.PreferencesHelper
import com.qpay.customer.repos.HomeRepository
import com.qpay.customer.repos.LoginRepository
import com.qpay.customer.repos.RegistrationRepository
import com.qpay.customer.ui.common.BaseViewModel
import com.qpay.customer.util.AppConstants
import com.qpay.customer.util.asFile
import com.qpay.customer.util.asFilePart
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoreBookListViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {

}