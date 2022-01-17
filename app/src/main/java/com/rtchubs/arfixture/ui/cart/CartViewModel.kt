package com.rtchubs.arfixture.ui.cart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.arfixture.api.*
import com.rtchubs.arfixture.local_db.dao.CartDao
import com.rtchubs.arfixture.local_db.dbo.CartItem
import com.rtchubs.arfixture.repos.OrderRepository
import com.rtchubs.arfixture.ui.common.BaseViewModel
import com.rtchubs.arfixture.util.AppConstants
import com.rtchubs.arfixture.models.order.OrderStoreBody
import com.rtchubs.arfixture.models.order.OrderStoreProduct
import com.rtchubs.shop.models.order.OrderStoreResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val application: Application,
    private val cartDao: CartDao,
    private val orderRepository: OrderRepository
) : BaseViewModel(application) {

    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val mobile: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val address: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val orderPlaceResponse: MutableLiveData<OrderStoreResponse> by lazy {
        MutableLiveData<OrderStoreResponse>()
    }

    val cartItems: LiveData<List<CartItem>> = liveData {
        cartDao.getCartItems().collect { list ->
            emit(list)
        }
    }

    val orderItems: MutableLiveData<List<OrderStoreProduct>> by lazy {
        MutableLiveData<List<OrderStoreProduct>>()
    }

    fun deleteCartItemsByIds(itemIds: List<Int>) {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        viewModelScope.launch(handler) {
            cartDao.deleteCartItemsByIds(itemIds)
        }
    }

    fun incrementOrderItemQuantity(id: Int) {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        viewModelScope.launch(handler) {
            cartDao.incrementCartItemQuantity(id)
        }
    }

    fun decrementOrderItemQuantity(id: Int) {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        viewModelScope.launch(handler) {
            cartDao.decrementCartItemQuantity(id)
        }
    }

    fun deleteCartItem(item: CartItem) {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        viewModelScope.launch(handler) {
            cartDao.deleteCartItem(item)
        }
    }

    fun deleteAllCartItems() {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        viewModelScope.launch(handler) {
            cartDao.deleteAllCartItems()
        }
    }

    fun placeOrder(orderStoreBody: OrderStoreBody) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(orderRepository.placeOrder(orderStoreBody))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        orderPlaceResponse.postValue(apiResponse.body)
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

    fun generateInvoiceID(): String {
        val random1 = "${1 + SecureRandom().nextInt(9999999)}"
        val random2 = "${1 + SecureRandom().nextInt(999999)}"
        return "IV-${random1}${random2}"
    }
}