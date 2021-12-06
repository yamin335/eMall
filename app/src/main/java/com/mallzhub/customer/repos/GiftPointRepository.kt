package com.mallzhub.customer.repos

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mallzhub.customer.api.ApiService
import com.mallzhub.customer.models.GiftPointStoreBody
import com.mallzhub.customer.models.GiftPointStoreResponse
import com.mallzhub.customer.models.GiftPointsHistoryDetailsResponse
import com.mallzhub.customer.models.ShopWiseGiftPointResponse
import com.mallzhub.customer.models.order.OrderListResponse
import com.mallzhub.customer.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiftPointRepository @Inject constructor(private val apiService: ApiService) {
//    suspend fun getOrderList(page: Int?, token: String?): Response<OrderListResponse> {
//        return withContext(Dispatchers.IO) {
//            apiService.getOrderList(page, token)
//        }
//    }

    suspend fun saveGiftPoints(giftPointStoreBody: GiftPointStoreBody): Response<GiftPointStoreResponse> {
        val jsonString = Gson().toJson(giftPointStoreBody)
        val jsonObject = JsonParser().parse(jsonString).asJsonObject
        return withContext(Dispatchers.IO) {
            apiService.saveGiftPoints(jsonObject)
        }
    }

    suspend fun getShopWiseGiftPoints(customerId: Int): Response<ShopWiseGiftPointResponse> {
        val jsonObject = JsonObject().apply {
            addProperty("customer_id", customerId)
        }
        return withContext(Dispatchers.IO) {
            apiService.getShopWiseGiftPoints(1, jsonObject)
        }
    }

    suspend fun getShopWiseGiftPointsDetails(customerId: Int, merchantId: Int): Response<GiftPointsHistoryDetailsResponse> {
        val jsonObject = JsonObject().apply {
            addProperty("customer_id", customerId)
            addProperty("merchant_id", merchantId)
        }
        return withContext(Dispatchers.IO) {
            apiService.getShopWiseGiftPointsDetails(1, jsonObject)
        }
    }
}