package com.rtchubs.arfixture.ui.product_ar_view

import android.app.Application
import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.arfixture.local_db.dao.CartDao
import com.rtchubs.arfixture.local_db.dbo.CartItem
import com.rtchubs.arfixture.models.Product
import com.rtchubs.arfixture.ui.common.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductARViewModel @Inject constructor(
    private val application: Application,
    private val cartDao: CartDao
    ) : BaseViewModel(application) {

    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        try {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
            }

            viewModelScope.launch(handler) {
                val response = cartDao.addItemToCart(CartItem(product.id, product, quantity))
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
}