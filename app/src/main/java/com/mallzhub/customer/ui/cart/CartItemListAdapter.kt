package com.mallzhub.customer.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.customer.AppExecutors
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.ShopWiseCartListItemBinding
import com.mallzhub.customer.local_db.dbo.CartItem
import com.mallzhub.customer.models.MerchantWiseOrder

import com.mallzhub.customer.util.DataBoundListAdapter
import com.mallzhub.customer.models.order.OrderStoreBody
import com.mallzhub.customer.models.order.OrderStoreProduct
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CartItemListAdapter(
    private val appExecutors: AppExecutors,
    private val cartItemActionCallback: CartItemActionCallback,
    private val deleteProductCallback: (CartItem) -> Unit,
    private val checkoutCallback: (MerchantWiseOrder) -> Unit

) : DataBoundListAdapter<MerchantWiseOrder, ShopWiseCartListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<MerchantWiseOrder>() {
        override fun areItemsTheSame(oldItem: MerchantWiseOrder, newItem: MerchantWiseOrder): Boolean {
            return oldItem.merchantId == newItem.merchantId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MerchantWiseOrder,
            newItem: MerchantWiseOrder
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): ShopWiseCartListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_shop_wise_cart, parent, false
        )
    }

    interface CartItemActionCallback {
        fun incrementCartItemQuantity(id: Int)
        fun decrementCartItemQuantity(id: Int)
    }

    override fun bind(binding: ShopWiseCartListItemBinding, position: Int) {
        val item = getItem(position)

        val cartProductListAdapter = CartProductListAdapter(appExecutors, object : CartProductListAdapter.CartItemActionCallback {
            override fun incrementCartItemQuantity(id: Int) {
                cartItemActionCallback.incrementCartItemQuantity(id)
            }

            override fun decrementCartItemQuantity(id: Int) {
                cartItemActionCallback.decrementCartItemQuantity(id)
            }

        }) {
            deleteProductCallback.invoke(it)
        }

        binding.rvCartItems.setHasFixedSize(true)
        binding.rvCartItems.adapter = cartProductListAdapter
        cartProductListAdapter.submitList(item.orderProductList)
        binding.totalPrice = item.totalPrice
        binding.shopName = item.merchantName
        binding.checkoutNow.setOnClickListener {
            checkoutCallback(item)
        }
    }
}