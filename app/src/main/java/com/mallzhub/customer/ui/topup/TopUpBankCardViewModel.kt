package com.mallzhub.customer.ui.topup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mallzhub.customer.api.*
import com.mallzhub.customer.models.common.MyAccountListResponse
import com.mallzhub.customer.prefs.PreferencesHelper
import com.mallzhub.customer.repos.TopUpRepository
import com.mallzhub.customer.ui.common.BaseViewModel
import com.mallzhub.customer.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopUpBankCardViewModel @Inject constructor(private val application: Application, private val repository: TopUpRepository, private val preferencesHelper: PreferencesHelper) : BaseViewModel(application) {

    val myAccountList: MutableLiveData<MyAccountListResponse> by lazy {
        MutableLiveData<MyAccountListResponse>()
    }

    fun getMyAccountList() {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse =
                    ApiResponse.create(repository.myAccountListRepo(preferencesHelper.getAccessTokenHeader()))) {
                    is ApiSuccessResponse -> {
                        myAccountList.postValue(apiResponse.body)
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        myAccountList.postValue(
                            Gson().fromJson(
                                apiResponse.errorMessage,
                                MyAccountListResponse::class.java
                            )
                        )
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }
    }
}