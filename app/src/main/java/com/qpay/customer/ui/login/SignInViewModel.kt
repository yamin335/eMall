package com.qpay.customer.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qpay.customer.api.*
import com.qpay.customer.models.registration.InquiryResponse
import com.qpay.customer.repos.RegistrationRepository
import com.qpay.customer.ui.common.BaseViewModel
import com.qpay.customer.util.AppConstants.serverConnectionErrorMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(private val application: Application, private val repository: RegistrationRepository) : BaseViewModel(application) {

    val mobileNo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun inquireAccount(mobileNumber: String, deviceId: String): LiveData<InquiryResponse> {
        val response: MutableLiveData<InquiryResponse> = MutableLiveData()
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
                        response.postValue(apiResponse.body)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }

        return response
    }
}