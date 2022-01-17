package com.rtchubs.arfixture.api

import com.google.gson.JsonObject
import com.rtchubs.arfixture.api.Api.ContentType
import com.rtchubs.arfixture.models.*
import com.rtchubs.arfixture.models.common.MyAccountListResponse
import com.rtchubs.arfixture.models.order.OrderListResponse
import com.rtchubs.arfixture.models.payment_account_models.AddCardOrBankResponse
import com.rtchubs.arfixture.models.payment_account_models.BankOrCardListResponse
import com.rtchubs.arfixture.models.registration.InquiryResponse
import com.rtchubs.arfixture.models.registration.DefaultResponse
import com.rtchubs.shop.models.order.OrderStoreResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * REST API access points
 */
interface ApiService {

    @Multipart
    @POST(ApiEndPoint.INQUIRE)
    suspend fun inquire(@Part("PhoneNumber") mobileNumber: RequestBody, @Part("DeviceId") deviceId: RequestBody): Response<InquiryResponse>

    @Multipart
    @POST(ApiEndPoint.REQUESTOTP)
    suspend fun requestOTP(
        @Part("PhoneNumber") mobileNumber: RequestBody,
        @Part("HasGivenConsent") hasGivenConsent: RequestBody
    ): Response<DefaultResponse>

    @Multipart
    @POST(ApiEndPoint.REGISTRATION)
    suspend fun registration(
        @Part("MobileNumber") mobileNumber: RequestBody,
        @Part("Otp") otp: RequestBody,
        @Part("Pin") password: RequestBody,
        @Part("FullName") fullName: RequestBody,
        @Part("MobileOperator") mobileOperator: RequestBody,
        @Part("DeviceId") deviceId: RequestBody,
        @Part("DeviceName") deviceName: RequestBody,
        @Part("DeviceModel") deviceModel: RequestBody,
        @Part userImage: MultipartBody.Part?,
        @Part("NidNumber") nidNumber: RequestBody,
        @Part nidFrontImage: MultipartBody.Part?,
        @Part nidBackImage: MultipartBody.Part?
    ): Response<DefaultResponse>


    @FormUrlEncoded
    @POST(ApiEndPoint.CONNECT_TOKEN)
    suspend fun connectToken(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String,
        @Field("device_id") deviceID: String,
        @Field("device_name") deviceName: String,
        @Field("device_model") deviceModel: String,
        @Field("client_id") clientID: String,
        @Field("client_secret") clientSecret: String,
        @Field("otp") otp: String
    ): Response<String>


    @GET(ApiEndPoint.GET_BANK_LIST)
    suspend fun requestBankList(
        @Query("type") type: String,
        @Header("Authorization") token: String
    ): Response<BankOrCardListResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.ADD_CARD)
    suspend fun addBankAccount(
        @Body jsonObject: JsonObject,
        @Header("Authorization") token: String
    ): Response<AddCardOrBankResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.ADD_BANK)
    suspend fun addCardAccount(
        @Body jsonObject: JsonObject,
        @Header("Authorization") token: String
    ): Response<AddCardOrBankResponse>

    @GET(ApiEndPoint.MY_ACCOUNT_LIST)
    suspend fun myAccountList(
        @Header("Authorization") token: String
    ): Response<MyAccountListResponse>


    // eDokanPat
    @GET(ApiEndPoint.ALL_MALL)
    suspend fun getAllMalls(): Response<AllShoppingMallResponse>

    @GET(ApiEndPoint.ALL_MERCHANTS)
    suspend fun getAllMerchants(): Response<AllMerchantResponse>

    @GET(ApiEndPoint.MERCHANT_PRODUCTS)
    suspend fun getAllProducts(
        @Path("id") type: Int?
    ): Response<AllProductResponse>

    @GET(ApiEndPoint.ORDER_LIST)
    suspend fun getOrderList(
        @Query("page") page: Int?,
        @Query("token") token: String?): Response<OrderListResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.SALE)
    suspend fun placeOrder(
        @Body jsonObject: JsonObject
    ): Response<OrderStoreResponse>

    @GET(ApiEndPoint.OFFER_LIST)
    suspend fun getOfferList(): Response<OfferProductListResponse>

    @GET(ApiEndPoint.PRODUCT_DETAILS)
    suspend fun getProductDetails(
        @Path("id") type: Int?
    ): Response<ProductDetailsResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.GIFT_POINT_COLLECT)
    suspend fun saveGiftPoints(
        @Body jsonObject: JsonObject
    ): Response<GiftPointStoreResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.SHOP_WISE_GIFT_POINT)
    suspend fun getShopWiseGiftPoints(
        @Query("page") page: Int?,
        @Body jsonObject: JsonObject
    ): Response<ShopWiseGiftPointResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.SHOP_WISE_GIFT_POINT)
    suspend fun getShopWiseGiftPointsDetails(
        @Query("page") page: Int?,
        @Body jsonObject: JsonObject
    ): Response<GiftPointsHistoryDetailsResponse>

}
