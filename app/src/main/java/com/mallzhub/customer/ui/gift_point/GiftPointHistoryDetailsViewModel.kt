package com.mallzhub.customer.ui.gift_point

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mallzhub.customer.models.GiftPointHistoryDetailsItem
import com.mallzhub.customer.repos.OrderRepository
import com.mallzhub.customer.ui.common.BaseViewModel
import javax.inject.Inject

class GiftPointHistoryDetailsViewModel @Inject constructor(
    private val application: Application,
    private val orderRepository: OrderRepository
) : BaseViewModel(application) {

    val giftPointsHistoryList: MutableLiveData<List<GiftPointHistoryDetailsItem>> by lazy {
        MutableLiveData<List<GiftPointHistoryDetailsItem>>()
    }

    fun getGiftPointHistory() {
        val list = ArrayList<GiftPointHistoryDetailsItem>()
        list.add(GiftPointHistoryDetailsItem(0, "T-Shirt", "Sample description Sample description Sample description", 8))
        list.add(GiftPointHistoryDetailsItem(2, "Pant", "Sample description", 10))
        list.add(GiftPointHistoryDetailsItem(3, "Snicker", "Sample description Sample description", 5))
        list.add(GiftPointHistoryDetailsItem(4, "Leather Shoe", "Sample description Sample description Sample description Sample description Sample description", 20))
        list.add(GiftPointHistoryDetailsItem(5, "Belt", "Sample description", 30))
        list.add(GiftPointHistoryDetailsItem(6, "Hat", "Sample description", 45))
        list.add(GiftPointHistoryDetailsItem(7, "Shocks", "Sample description Sample description Sample description", 22))
        giftPointsHistoryList.postValue(list)
    }

}