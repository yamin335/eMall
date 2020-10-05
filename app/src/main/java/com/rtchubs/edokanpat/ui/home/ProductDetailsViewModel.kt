package com.rtchubs.edokanpat.ui.home

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.local_db.dao.CartDao
import com.rtchubs.edokanpat.local_db.dbo.CartItem
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.models.registration.DefaultResponse
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val application: Application,
    private val cartDao: CartDao
) : BaseViewModel(application) {

    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }

    fun doesItemExists(item: Product): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        viewModelScope.launch(handler) {
            cartDao.doesItemExists(item.id).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun addToCart(product: Product, quantity: Int) {
        try {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
            }

            viewModelScope.launch(handler) {
                cartDao.addItemToCart(CartItem(product.id, product.name, product.barcode, product.mrp, quantity, product.category_id, product.merchant_id))
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }
    }
}