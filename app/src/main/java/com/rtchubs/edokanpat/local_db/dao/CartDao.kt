package com.rtchubs.edokanpat.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rtchubs.edokanpat.local_db.dbo.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addItemToCart(item: CartItem)

    @Query("SELECT * FROM cart")
    suspend fun getCartItems(): List<CartItem>
}