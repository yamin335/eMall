package com.rtchubs.edokanpat.ui.cart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rtchubs.edokanpat.local_db.dao.CartDao
import com.rtchubs.edokanpat.local_db.dbo.CartItem
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import javax.inject.Inject

class CartViewModel @Inject constructor(private val application: Application, private val cartDao: CartDao) : BaseViewModel(application) {

    val cartItems: LiveData<List<CartItem>> = liveData {
        val data = cartDao.getCartItems() // loadUser is a suspend function.
        emit(data)
    }

}