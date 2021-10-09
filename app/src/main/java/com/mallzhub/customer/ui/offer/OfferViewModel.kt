package com.mallzhub.customer.ui.offer

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mallzhub.customer.local_db.dao.CartDao
import com.mallzhub.customer.ui.common.BaseViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class OfferViewModel @Inject constructor(
    private val application: Application,
    private val cartDao: CartDao
) : BaseViewModel(application) {

    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }
}