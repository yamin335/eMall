package com.rtchubs.edokanpat.ui.shops

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.models.registration.DefaultResponse
import com.rtchubs.edokanpat.models.registration.InquiryResponse
import com.rtchubs.edokanpat.repos.RegistrationRepository
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants.serverConnectionErrorMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopDetailsContactUsViewModel @Inject constructor(private val application: Application) : BaseViewModel(application) {

}