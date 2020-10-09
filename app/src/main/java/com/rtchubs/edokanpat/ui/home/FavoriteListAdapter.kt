package com.rtchubs.edokanpat.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rtchubs.edokanpat.AppExecutors
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.FavoriteListItemBinding
import com.rtchubs.edokanpat.databinding.MoreShoppingListItemBinding
import com.rtchubs.edokanpat.databinding.ProductListItemBinding
import com.rtchubs.edokanpat.databinding.ShopListItemBinding
import com.rtchubs.edokanpat.models.Merchant

import com.rtchubs.edokanpat.models.PaymentMethod
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.util.DataBoundListAdapter
import kotlinx.android.synthetic.main.popup_menu_product_item.view.*

class FavoriteListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((Product) -> Unit)? = null
) : DataBoundListAdapter<Product, FavoriteListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    // Properties
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    val onClicked = MutableLiveData<Product>()
    override fun createBinding(parent: ViewGroup): FavoriteListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_favorite, parent, false
        )
    }


    override fun bind(binding: FavoriteListItemBinding, position: Int) {
        val item = getItem(position)
        binding.item = item
        binding.imageUrl = item.thumbnail

        binding.remove.setOnClickListener {
            itemCallback?.invoke(item)
        }

        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.thumbnail.setImageResource(R.drawable.product_image)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }
    }
}