package com.rtchubs.arfixture.ui.cart

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rtchubs.arfixture.AppExecutors
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.CartListItemBinding
import com.rtchubs.arfixture.local_db.dbo.CartItem

import com.rtchubs.arfixture.util.DataBoundListAdapter

class CartProductListAdapter(
    private val appExecutors: AppExecutors,
    private val cartItemActionCallback: CartItemActionCallback,
    private val deleteProductCallback: ((CartItem) -> Unit)? = null

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

    override fun createBinding(parent: ViewGroup): CartListItemBinding {
        return DataBindingUtil.inflate(
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

        val mrp = item.product.mrp ?: 0.0
        val discountedPrice = item.product.discountedPrice ?: 0.0

        val context = binding.root.context
        binding.mrp = "${context.getString(R.string.sign_taka)} $mrp"
        binding.discountedPrice = "${context.getString(R.string.sign_taka)} $discountedPrice"

        if (discountedPrice > 0.0) {
            binding.tvMrp.visibility = View.GONE
            binding.tvOldPrice.visibility = View.VISIBLE
            binding.tvNewPrice.visibility = View.VISIBLE
            binding.tvOldPrice.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        } else {
            binding.tvMrp.visibility = View.VISIBLE
            binding.tvOldPrice.visibility = View.GONE
            binding.tvNewPrice.visibility = View.GONE
            if ((binding.tvOldPrice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) > 0) {
                binding.tvOldPrice.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }

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
            deleteProductCallback?.invoke(item)
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