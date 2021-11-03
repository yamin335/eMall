package com.mallzhub.customer.local_db.dao

import androidx.room.*
import com.mallzhub.customer.local_db.dbo.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItemToCart(item: CartItem): Long

    @Query("DELETE FROM cart")
    suspend fun deleteAllCartItems()

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("SELECT * FROM cart")
    fun getCartItems(): Flow<List<CartItem>>

    @Query("SELECT COUNT(id) FROM cart")
    fun getCartItemsCount(): Flow<Int>

    @Query("UPDATE cart SET quantity = quantity + 1 WHERE id = :id")
    suspend fun incrementCartItemQuantity(id: Int)

    @Query("UPDATE cart SET quantity = quantity - 1 WHERE id = :id")
    suspend fun decrementCartItemQuantity(id: Int)
}