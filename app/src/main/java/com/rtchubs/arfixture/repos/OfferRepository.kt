package com.rtchubs.arfixture.repos

import com.rtchubs.arfixture.api.ApiService
import com.rtchubs.arfixture.models.OfferProductListResponse
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