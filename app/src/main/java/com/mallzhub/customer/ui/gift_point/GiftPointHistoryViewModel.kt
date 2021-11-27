package com.mallzhub.customer.ui.gift_point

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mallzhub.customer.models.GiftPointHistoryItem
import com.mallzhub.customer.repos.OrderRepository
import com.mallzhub.customer.ui.common.BaseViewModel
import javax.inject.Inject

class GiftPointHistoryViewModel @Inject constructor(
    private val application: Application,
    private val orderRepository: OrderRepository
) : BaseViewModel(application) {

    val giftPointsHistoryList: MutableLiveData<List<GiftPointHistoryItem>> by lazy {
        MutableLiveData<List<GiftPointHistoryItem>>()
    }

    fun getGiftPointHistory() {
        val list = ArrayList<GiftPointHistoryItem>()
        list.add(GiftPointHistoryItem(0, "Cats Eye", 50))
        list.add(GiftPointHistoryItem(2, "Lotto", 50))
        list.add(GiftPointHistoryItem(3, "Ellyien", 50))
        list.add(GiftPointHistoryItem(4, "Estasy", 50))
        list.add(GiftPointHistoryItem(5, "Bata", 50))
        list.add(GiftPointHistoryItem(6, "Apex", 50))
        list.add(GiftPointHistoryItem(7, "Hush Puppy", 50))
        giftPointsHistoryList.postValue(list)
    }

}