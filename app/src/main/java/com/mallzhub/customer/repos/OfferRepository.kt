package com.mallzhub.customer.repos

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.mallzhub.customer.api.ApiService
import com.mallzhub.customer.models.OfferProductListResponse
import com.mallzhub.customer.models.order.OrderListResponse
import com.mallzhub.customer.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getOfferList(): Response<OfferProductListResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getOfferList()
        }
    }
}