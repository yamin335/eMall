package com.rtchubs.arfixture.models

import java.io.Serializable

data class AllMerchantResponse(val code: Int?, val status: String?, val message: String?, val data: List<Merchant>?)

//data class Merchant(val id: Int?, val name: String?, val user_name: String?,
//                    val password: String?, val shop_name: String?,
//                    val mobile: String?, val lat: Double?,
//                    val long: Double?, val whatsApp: String?,
//                    val email: String?, val address: String?,
//                    val shop_address: String?, val shop_logo: String?,
//                    val thumbnail: String?, val isActive: Int?,
//                    val shopping_mall_id: Int?, val shopping_mall_level_id: Int?,
//                    val created_at: String?, val updated_at: String?,
//                    val shopping_mall: ShoppingMall?): Serializable

data class Merchant(val id: Int?, val name: String?, val user_name: String?,
                                val shop_name: String?, val mobile: String?, val lat: Double?,
                                val long: Double?, val whatsApp: String?, val email: String?,
                                val address: String?, val shop_address: String?, val shop_logo: String?,
                                val thumbnail: String?, val isActive: Int?, val shopping_mall_id: Int?,
                                val shopping_mall_level_id: Int?, val rent_date: String?,
                                val monthly_rent: Any?, val advance_pament: Any?, val advance_payment_date: String?,
                                val agreement_duration: Any?, val created_at: String?, val updated_at: String?,
                                val type: String?, val offer_discount_type: Any?, val offer_discount_percent: Any?,
                                val offer_banner: String?, val offer_valid_from: String?, val offer_valid_to: String?,
                                val shopping_mall: ShoppingMall?): Serializable
