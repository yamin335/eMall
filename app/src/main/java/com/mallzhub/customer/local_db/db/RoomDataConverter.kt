package com.mallzhub.customer.local_db.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mallzhub.customer.models.Merchant
import com.mallzhub.customer.models.Product
import com.mallzhub.customer.models.ProductCategory

class RoomDataConverter {
    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun jsonStringToCategory(value: String?): ProductCategory? {
        return gson.fromJson(value, ProductCategory::class.java)
    }

    @TypeConverter
    fun categoryToJsonString(category: ProductCategory?): String? {
        return gson.toJson(category)
    }

    @TypeConverter
    fun jsonStringToProduct(value: String): Product {
        return gson.fromJson(value, Product::class.java)
    }

    @TypeConverter
    fun productToJsonString(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun jsonStringToMerchant(value: String): Merchant {
        return gson.fromJson(value, Merchant::class.java)
    }

    @TypeConverter
    fun merchantToJsonString(merchant: Merchant): String {
        return gson.toJson(merchant)
    }

//    @TypeConverter
//    fun jsonStringToOrderProduct(value: String): Product {
//        return gson.fromJson(value, Product::class.java)
//    }

//    @TypeConverter
//    fun orderProductToJsonString(product: Product): String {
//        return gson.toJson(product)
//    }

//    @TypeConverter
//    fun fromString(value: String?): ArrayList<String?>? {
//        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
//        return gson.fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromArrayLisr(list: ArrayList<String?>?): String? {
//        return gson.toJson(list)
//    }

}
