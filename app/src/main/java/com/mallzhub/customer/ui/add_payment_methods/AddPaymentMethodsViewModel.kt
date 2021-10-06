package com.mallzhub.customer.ui.add_payment_methods

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mallzhub.customer.api.*
import com.mallzhub.customer.models.payment_account_models.BankOrCardListResponse
import com.mallzhub.customer.prefs.PreferencesHelper
import com.mallzhub.customer.repos.HomeRepository
import com.mallzhub.customer.ui.common.BaseViewModel
import com.mallzhub.customer.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPaymentMethodsViewModel @Inject constructor(
    private val preferencesHelper: PreferencesHelper,
    private val application: Application,
    private val repository: HomeRepository
) : BaseViewModel(application) {
    val bankOrCardListResponse: MutableLiveData<BankOrCardListResponse> by lazy {
        MutableLiveData<BankOrCardListResponse>()
    }

    fun requestBankList(type:String) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse =
                    ApiResponse.create(repository.requestBankListRepo(type, preferencesHelper.getAccessTokenHeader()))) {
                    is ApiSuccessResponse -> {
                        bankOrCardListResponse.postValue(apiResponse.body)
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        bankOrCardListResponse.postValue(
                            Gson().fromJson(
                                apiResponse.errorMessage,
                                BankOrCardListResponse::class.java
                            )
                        )
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }
    }
}