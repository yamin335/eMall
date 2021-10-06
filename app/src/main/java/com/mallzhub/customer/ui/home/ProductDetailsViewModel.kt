package com.mallzhub.customer.ui.home

import android.app.Application
import android.database.sqlite.SQLiteException
import androidx.lifecycle.*
import com.mallzhub.customer.local_db.dao.CartDao
import com.mallzhub.customer.local_db.dao.FavoriteDao
import com.mallzhub.customer.local_db.dbo.CartItem
import com.mallzhub.customer.models.OrderProduct
import com.mallzhub.customer.models.Product
import com.mallzhub.customer.ui.common.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val application: Application,
    private val cartDao: CartDao,
    private val favoriteDao: FavoriteDao
) : BaseViewModel(application) {

    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }

//    fun doesItemExistsInCart(item: Product): LiveData<Boolean> {
//        val result = MutableLiveData<Boolean>()
//        val handler = CoroutineExceptionHandler { _, exception ->
//            exception.printStackTrace()
//        }
//
//        viewModelScope.launch(handler) {
//            cartDao.doesItemExists(item.id).collect {
//                result.postValue(it)
//            }
//        }
//        return result
//    }
//
//    fun doesItemExistsInFavorite(item: Product): LiveData<Boolean> {
//        val result = MutableLiveData<Boolean>()
//        val handler = CoroutineExceptionHandler { _, exception ->
//            exception.printStackTrace()
//        }
//
//        viewModelScope.launch(handler) {
//            favoriteDao.doesItemExistsInFavorite(item.id).collect {
//                result.postValue(it)
//            }
//        }
//        return result
//    }

    fun addToCart(product: OrderProduct, quantity: Int) {
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
}