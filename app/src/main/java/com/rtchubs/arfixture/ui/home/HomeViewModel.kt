package com.rtchubs.arfixture.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.api.*
import com.rtchubs.arfixture.local_db.dao.CartDao
import com.rtchubs.arfixture.models.*
import com.rtchubs.arfixture.models.registration.DefaultResponse
import com.rtchubs.arfixture.prefs.PreferencesHelper
import com.rtchubs.arfixture.repos.HomeRepository
import com.rtchubs.arfixture.ui.common.BaseViewModel
import com.rtchubs.arfixture.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val preferencesHelper: PreferencesHelper,
    private val application: Application,
    private val repository: HomeRepository,
    private val cartDao: CartDao
) : BaseViewModel(application) {

    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }
    // eDokanPat
    val allShoppingMallResponse: MutableLiveData<AllShoppingMallResponse> by lazy {
        MutableLiveData<AllShoppingMallResponse>()
    }

    val allShopListResponse: MutableLiveData<AllMerchantResponse> by lazy {
        MutableLiveData<AllMerchantResponse>()
    }

    val defaultResponse: MutableLiveData<DefaultResponse> = MutableLiveData()
    val doctorList: List<Book>
        get() = listOf(
            Book(
                "0",
                "Top Rated",
                listOf(
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_1
                    ),
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_2
                    ),
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_3
                    )
                )
            ),
            Book(
                "1",
                "Favourites",
                listOf(
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_4
                    ),
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_13
                    ),
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_6
                    )
                )
            ),
            Book(
                "2",
                "Top Rated",
                listOf(
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_7
                    ),
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_8
                    ),
                    SubBook(
                        "1",
                        "Angelica Hale",
                        "Dhaka",
                        "",
                        R.drawable.book_9
                    )
                )
            )
        )


    val paymentMethodList: List<PaymentMethod>
        get() = listOf(
            /* PaymentMethod(
                 "0",
                 "\u2022\u2022\u2022\u2022 4122",R.drawable.maestro

             ),
             PaymentMethod(
                 "1",
                 "\u2022\u2022\u2022\u2022 9120",R.drawable.visa
             ),*/
            PaymentMethod(
                "-1",
                "Add Payment Method", R.drawable.plus
            )
        )


    val slideDataList = listOf(
        SlideData(R.drawable.slider_image_1, "Ads1", "Now Easy and Fast Shopping"),
        SlideData(R.drawable.slider_image_1, "Ads2", "Now Easy and Fast Shopping"),
        SlideData(R.drawable.slider_image_1, "Ads3", "Now Easy and Fast Shopping"),
        SlideData(R.drawable.slider_image_1, "Ads4", "Now Easy and Fast Shopping"),
        SlideData(R.drawable.slider_image_1, "Ads5", "Now Easy and Fast Shopping")
    )

    inner class SlideData(
        var slideImage: Int,
        var textTitle: String,
        var descText: String
    )


    fun requestBankList(type:String) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            Log.e("token", preferencesHelper.getAccessTokenHeader())
            viewModelScope.launch(handler) {
                when (val apiResponse =
                    ApiResponse.create(repository.requestBankListRepo(type,preferencesHelper.getAccessTokenHeader()))) {
                    is ApiSuccessResponse -> {
                        var d=DefaultResponse(apiResponse.body.toString(), "", "", true)
                        defaultResponse.postValue(d)
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        Log.e("error", apiResponse.errorMessage)
                        defaultResponse.postValue(
                            Gson().fromJson(
                                apiResponse.errorMessage,
                                DefaultResponse::class.java
                            )
                        )
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }
    }

    fun getAllShopList() {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.getAllMerchantsRepo())) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        allShopListResponse.postValue(apiResponse.body)
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

    // eDokanPat
    fun getAllShoppingMallList() {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.getAllMallsRepo())) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        allShoppingMallResponse.postValue(apiResponse.body)
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