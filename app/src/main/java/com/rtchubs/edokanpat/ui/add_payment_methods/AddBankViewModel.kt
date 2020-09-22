package com.rtchubs.edokanpat.ui.add_payment_methods

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.models.payment_account_models.AddCardOrBankResponse
import com.rtchubs.edokanpat.prefs.PreferencesHelper
import com.rtchubs.edokanpat.repos.HomeRepository
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddBankViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository,
    private val preferencesHelper: PreferencesHelper
) : BaseViewModel(application) {

    var bankId = 0
    val bankAccount: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val addBankAccountResponse: MutableLiveData<AddCardOrBankResponse> by lazy {
        MutableLiveData<AddCardOrBankResponse>()
    }

    fun addBankAccount() {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {

                when (val apiResponse = ApiResponse.create(repository.addBankRepo(bankId,bankAccount.value!!, preferencesHelper.getAccessTokenHeader()))) {
                    is ApiSuccessResponse -> {
                        addBankAccountResponse.postValue(apiResponse.body)
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        addBankAccountResponse.postValue(Gson().fromJson(apiResponse.errorMessage, AddCardOrBankResponse::class.java))
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }
    }
}