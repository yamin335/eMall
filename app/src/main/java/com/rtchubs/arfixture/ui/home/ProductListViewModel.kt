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
import com.rtchubs.arfixture.models.FileDownloadResponse
import com.rtchubs.arfixture.models.Merchant
import com.rtchubs.arfixture.models.Product
import com.rtchubs.arfixture.repos.HomeRepository
import com.rtchubs.arfixture.ui.common.BaseViewModel
import com.rtchubs.arfixture.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import kotlin.math.abs

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

    val showHideProgress: MutableLiveData<Pair<Boolean, Int>> by lazy {
        MutableLiveData<Pair<Boolean, Int>>()
    }

    val fileDownloadResponse: MutableLiveData<FileDownloadResponse> by lazy {
        MutableLiveData<FileDownloadResponse>()
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

    fun downloadFileFromUrl(downloadUrl: String, filePath: String, fileName: String) {
        if (checkNetworkStatus()) {
            showHideProgress.postValue(Pair(true, 0))
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                showHideProgress.postValue(Pair(false, 100))
                fileDownloadResponse.postValue(FileDownloadResponse(false, filePath, fileName))
            }

            viewModelScope.launch(handler) {
                showHideProgress.postValue(Pair(false, 100))
                fileDownloadResponse.postValue(downloadFile(downloadUrl, filePath, fileName))
//                if (response == null) {
//                    showHideProgress.postValue(Pair(false, 100))
//                    pdfDownloadResponse.postValue(Pair(false, ""))
//                } else {
//                    pdfDownloadResponse.postValue(Pair(true, "${response.first}/${response.second}"))
//                }
            }
        }
    }

    private suspend fun downloadFile(downloadUrl: String, filePath: String, fileName: String): FileDownloadResponse {
        return withContext(Dispatchers.IO) {
            // Normally we would do some work here, like download a file.
            try {
                var currentProgress: Int
                var downloadedSize = 0
                val urlConnection: HttpURLConnection
                val url = URL(downloadUrl)
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                //urlConnection.doOutput = true
                urlConnection.connect()

                val downloadedFile = File(
                    filePath,
                    fileName
                )
                if (!downloadedFile.exists()) {
                    downloadedFile.createNewFile()
                }
                val inputStream: InputStream = urlConnection.inputStream
                val fileOutputStream = FileOutputStream(downloadedFile)
                val totalSize = urlConnection.contentLength
                val buffer = ByteArray(2024)
                var bufferLength: Int
                fileOutputStream.use { outputStream ->
                    inputStream.use { inStream ->
                        while (inStream.read(buffer).also { bufferLength = it } > 0) {
                            outputStream.write(buffer, 0, bufferLength)
                            downloadedSize += bufferLength
                            currentProgress = abs(downloadedSize * 100 / totalSize)
                            showHideProgress.postValue(Pair(true, currentProgress))
                        }
                    }
                }
                FileDownloadResponse(true, filePath, fileName)
            } catch (e: Exception) {
                e.printStackTrace()
                FileDownloadResponse(false, filePath, fileName)
            }
        }
    }
}