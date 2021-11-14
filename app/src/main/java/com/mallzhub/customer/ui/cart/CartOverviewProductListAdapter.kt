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
import com.mallzhub.customer.databinding.CartOverviewListItemBinding
import com.mallzhub.customer.local_db.dbo.CartItem

import com.mallzhub.customer.util.DataBoundListAdapter

class CartOverviewProductListAdapter(
    private val appExecutors: AppExecutors
) : DataBoundListAdapter<CartItem, CartOverviewListItemBinding>(
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

    override fun createBinding(parent: ViewGroup): CartOverviewListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_cart_overview, parent, false
        )
    }

    override fun bind(binding: CartOverviewListItemBinding, position: Int) {
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

        val quantity = item.quantity ?: 0
        val unitPrice = item.product.mrp?.toInt() ?: 0
        binding.subTotal = "${quantity * unitPrice}"
    }
}