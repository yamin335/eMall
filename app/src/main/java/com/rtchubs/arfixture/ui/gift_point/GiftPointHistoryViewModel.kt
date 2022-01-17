package com.rtchubs.arfixture.ui.gift_point

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.arfixture.api.*
import com.rtchubs.arfixture.models.ShopWiseGiftPointRewards
import com.rtchubs.arfixture.repos.GiftPointRepository
import com.rtchubs.arfixture.ui.common.BaseViewModel
import com.rtchubs.arfixture.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class GiftPointHistoryViewModel @Inject constructor(
    private val application: Application,
    private val giftPointRepository: GiftPointRepository
) : BaseViewModel(application) {

    val giftPointsHistoryList: MutableLiveData<List<ShopWiseGiftPointRewards>> by lazy {
        MutableLiveData<List<ShopWiseGiftPointRewards>>()
    }

    fun getShopWiseGiftPoints(customerId: Int) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(giftPointRepository.getShopWiseGiftPoints(customerId))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        giftPointsHistoryList.postValue(apiResponse.body.data?.rewards)
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