package com.rtchubs.edokanpat.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.models.registration.InquiryResponse
import com.rtchubs.edokanpat.repos.RegistrationRepository
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants.serverConnectionErrorMessage
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