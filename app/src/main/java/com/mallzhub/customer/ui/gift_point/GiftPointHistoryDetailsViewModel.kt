package com.mallzhub.customer.ui.gift_point

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.customer.api.*
import com.mallzhub.customer.models.GiftPointHistoryDetailsItem
import com.mallzhub.customer.models.GiftPointsHistoryDetailsResponseData
import com.mallzhub.customer.models.GiftPointsHistoryDetailsRewards
import com.mallzhub.customer.models.ShopWiseGiftPointRewards
import com.mallzhub.customer.repos.GiftPointRepository
import com.mallzhub.customer.repos.OrderRepository
import com.mallzhub.customer.ui.common.BaseViewModel
import com.mallzhub.customer.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class GiftPointHistoryDetailsViewModel @Inject constructor(
    private val application: Application,
    private val giftPointRepository: GiftPointRepository
) : BaseViewModel(application) {

    val giftPointsHistoryDetailsResponse: MutableLiveData<GiftPointsHistoryDetailsResponseData> by lazy {
        MutableLiveData<GiftPointsHistoryDetailsResponseData>()
    }

    fun getGiftPointsHistoryDetails(customerId: Int, merchantId: Int) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(giftPointRepository.getShopWiseGiftPointsDetails(customerId, merchantId))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        giftPointsHistoryDetailsResponse.postValue(apiResponse.body.data)
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