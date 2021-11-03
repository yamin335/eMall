package com.mallzhub.customer.ui.cart

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mallzhub.customer.AppExecutors
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.CartListItemBinding
import com.mallzhub.customer.local_db.dbo.CartItem

import com.mallzhub.customer.util.DataBoundListAdapter

class CartItemListAdapter(
    private val appExecutors: AppExecutors,
    private val cartItemActionCallback: CartItemActionCallback,
    private val itemCallback: ((CartItem) -> Unit)? = null

) : DataBoundListAdapter<CartItem, CartListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: CartItem,
            newItem: CartItem
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    // Properties
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    val onClicked = MutableLiveData<CartItem>()
    override fun createBinding(parent: ViewGroup): CartListItemBinding {
        return DataBindingUtil.inflate<CartListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_cart, parent, false
        )
    }

    interface CartItemActionCallback {
        fun incrementCartItemQuantity(id: Int)
        fun decrementCartItemQuantity(id: Int)
    }

    override fun bind(binding: CartListItemBinding, position: Int) {
        val item = getItem(position)
        binding.item = item

        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.thumbnail.setImageResource(R.drawable.image_placeholder)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }

        binding.remove.setOnClickListener {
            itemCallback?.invoke(item)
        }

        binding.incrementQuantity.setOnClickListener {
            cartItemActionCallback.incrementCartItemQuantity(item.id)
        }
        binding.decrementQuantity.setOnClickListener {
            if (item.quantity ?: 0 > 1) {
                cartItemActionCallback.decrementCartItemQuantity(item.id)
            }
        }
    }


}