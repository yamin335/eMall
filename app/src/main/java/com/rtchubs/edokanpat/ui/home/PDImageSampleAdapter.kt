package com.rtchubs.edokanpat.ui.home

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.rtchubs.edokanpat.databinding.MoreShoppingListItemBinding
import com.rtchubs.edokanpat.databinding.PDImageSampleBinding
import com.rtchubs.edokanpat.databinding.ProductListItemBinding
import com.rtchubs.edokanpat.databinding.ShopListItemBinding
import com.rtchubs.edokanpat.models.Merchant

import com.rtchubs.edokanpat.models.PaymentMethod
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.util.DataBoundListAdapter

class PDImageSampleAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((String) -> Unit)? = null

) : DataBoundListAdapter<String, PDImageSampleBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    // Properties
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    val onClicked = MutableLiveData<String>()
    private var selectedItemIndex = -1

    override fun createBinding(parent: ViewGroup): PDImageSampleBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_product_details_image_sample, parent, false
        )
    }


    override fun bind(binding: PDImageSampleBinding, position: Int) {
        val item = getItem(position)

        binding.isSelected = selectedItemIndex == position
        binding.imageUrl = item

        binding.root.setOnClickListener {
            selectedItemIndex = position
            itemCallback?.invoke(item)
            notifyDataSetChanged()
        }

        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.imageView.setImageResource(R.drawable.product_image)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }
    }
}