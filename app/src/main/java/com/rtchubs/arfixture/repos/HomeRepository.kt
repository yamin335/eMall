package com.rtchubs.arfixture.repos

import com.google.gson.JsonObject
import com.rtchubs.arfixture.api.ApiService
import com.rtchubs.arfixture.models.AllMerchantResponse
import com.rtchubs.arfixture.models.AllProductResponse
import com.rtchubs.arfixture.models.AllShoppingMallResponse
import com.rtchubs.arfixture.models.ProductDetailsResponse
import com.rtchubs.arfixture.models.payment_account_models.AddCardOrBankResponse
import com.rtchubs.arfixture.models.payment_account_models.BankOrCardListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun requestBankListRepo(type:String,token:String): Response<BankOrCardListResponse> {
        return withContext(Dispatchers.IO) {
            apiService.requestBankList(type,token)
        }
    }

    suspend fun addBankRepo(bankId: Int, accountNumber: String, token: String): Response<AddCardOrBankResponse> {
        val jsonObjectBody = JsonObject().apply {
            addProperty("bankId", bankId)
            addProperty("accountNumber", accountNumber)
        }

        return withContext(Dispatchers.IO) {
            apiService.addBankAccount(jsonObjectBody, token)
        }
    }

    suspend fun addCardRepo(bankId: Int, cardNumber: String, expireMonth: Int, expireYear: Int, token: String): Response<AddCardOrBankResponse> {
        val jsonObjectBody = JsonObject().apply {
            addProperty("bankId", bankId)
            addProperty("cardNumber", cardNumber)
            addProperty("expireMonth", expireMonth)
            addProperty("expireYear", expireYear)
        }

        return withContext(Dispatchers.IO) {
            apiService.addCardAccount(jsonObjectBody, token)
        }
    }

    // eDokanPat
    suspend fun getAllMallsRepo(): Response<AllShoppingMallResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getAllMalls()
        }
    }

    suspend fun getAllMerchantsRepo(): Response<AllMerchantResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getAllMerchants()
        }
    }

    suspend fun getAllProductsRepo(id: Int?): Response<AllProductResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getAllProducts(id)
        }
    }

    suspend fun getProductDetailsRepo(id: Int?): Response<ProductDetailsResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getProductDetails(id)
        }
    }
}