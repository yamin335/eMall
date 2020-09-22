package com.rtchubs.edokanpat.ui.otp_signin

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

class OtpSignInViewModel @Inject constructor(private val application: Application, private val repository: RegistrationRepository) : BaseViewModel(application) {

    val otp: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val registeredOTP: MutableLiveData<InquiryResponse> by lazy {
        MutableLiveData<InquiryResponse>()
    }

    val defaultResponse: MutableLiveData<DefaultResponse> = MutableLiveData()

    fun requestOTP(mobileNumber: String, hasGivenConsent: String) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.requestOTPRepo(mobileNumber, hasGivenConsent))) {
                    is ApiSuccessResponse -> {
                        Log.e("ress",apiResponse.body.body.toString())
                        defaultResponse.postValue(apiResponse.body)
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        defaultResponse.postValue(Gson().fromJson(apiResponse.errorMessage, DefaultResponse::class.java))
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }
    }

    fun requestOTPForRegisteredUser(mobileNumber: String, deviceId: String) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.inquireRepo(mobileNumber, deviceId))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        registeredOTP.postValue(apiResponse.body)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        registeredOTP.postValue(Gson().fromJson(apiResponse.errorMessage, InquiryResponse::class.java))
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }
    }
}