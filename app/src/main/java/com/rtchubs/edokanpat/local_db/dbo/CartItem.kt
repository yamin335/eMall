package com.rtchubs.edokanpat.local_db.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rtchubs.edokanpat.models.Product

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "product") val product: Product,
    @ColumnInfo(name = "quantity") val quantity: Int?
)