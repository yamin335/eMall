package com.rtchubs.arfixture.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.arfixture.api.*
import com.rtchubs.arfixture.local_db.dao.CartDao
import com.rtchubs.arfixture.models.AllMerchantResponse
import com.rtchubs.arfixture.repos.HomeRepository
import com.rtchubs.arfixture.ui.common.BaseViewModel
import com.rtchubs.arfixture.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllShopListViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository,
    private val cartDao: CartDao
) : BaseViewModel(application) {
    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }
    val allShopListResponse: MutableLiveData<AllMerchantResponse> by lazy {
        MutableLiveData<AllMerchantResponse>()
    }

    fun getAllShopList() {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.getAllMerchantsRepo())) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        allShopListResponse.postValue(apiResponse.body)
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
    }
}