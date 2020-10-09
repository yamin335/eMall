package com.rtchubs.edokanpat.ui.home

import android.app.Application
import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.local_db.dao.CartDao
import com.rtchubs.edokanpat.local_db.dao.FavoriteDao
import com.rtchubs.edokanpat.local_db.dbo.CartItem
import com.rtchubs.edokanpat.models.AllProductResponse
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.repos.HomeRepository
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val application: Application,
    private val favoriteDao: FavoriteDao,
    private val cartDao: CartDao
) : BaseViewModel(application) {

    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }

    val favoriteItems: LiveData<List<Product>> = liveData {
        favoriteDao.getFavoriteItems().collect { list ->
            emit(list)
        }
    }

    fun deleteFavoriteItem(item: Product) {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        viewModelScope.launch(handler) {
            favoriteDao.deleteFavoriteItem(item)
        }
    }
}