package com.rtchubs.edokanpat.local_db.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "barcode") val barcode: String?,
    @ColumnInfo(name = "mrp") val mrp: Double?,
    @ColumnInfo(name = "quantity") val quantity: Int?,
    @ColumnInfo(name = "category_id") val categoryID: Int?,
    @ColumnInfo(name = "merchant_id") val merchantID: Int?
)