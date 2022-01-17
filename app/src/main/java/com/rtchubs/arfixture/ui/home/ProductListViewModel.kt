package com.rtchubs.arfixture.ui.home

import android.app.Application
import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.arfixture.api.*
import com.rtchubs.arfixture.local_db.dao.CartDao
import com.rtchubs.arfixture.local_db.dao.FavoriteDao
import com.rtchubs.arfixture.local_db.dbo.CartItem
import com.rtchubs.arfixture.models.Merchant
import com.rtchubs.arfixture.models.Product
import com.rtchubs.arfixture.repos.HomeRepository
import com.rtchubs.arfixture.ui.common.BaseViewModel
import com.rtchubs.arfixture.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository,
    private val cartDao: CartDao,
    private val favoriteDao: FavoriteDao
) : BaseViewModel(application) {

    val productListResponse: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }

    fun addToFavorite(product: Product) {
        try {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
            }

            viewModelScope.launch(handler) {
                val response = favoriteDao.addItemToFavorite(product)
                if (response == -1L) {
                    toastWarning.postValue("Already added to favorite!")
                } else {
                    toastSuccess.postValue("Added to favorite")
                }
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        try {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
            }

            viewModelScope.launch(handler) {
                val response = cartDao.addItemToCart(CartItem(product.id ?: 0, product, quantity))
                if (response == -1L) {
                    toastWarning.postValue("Already added to cart!")
                } else {
                    toastSuccess.postValue("Added to cart")
                }
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }

    fun getProductList(merchant: Merchant) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.getAllProductsRepo(id = merchant.id))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        val productList = apiResponse.body.data ?: ArrayList()
                        productList.map { it.merchant = merchant }
                        productListResponse.postValue(productList)
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