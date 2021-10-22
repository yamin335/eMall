package com.mallzhub.customer.ui.order

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mallzhub.customer.AppExecutors
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.OrderDetailsProductListItemBinding
import com.mallzhub.customer.models.order.SalesDetails
import com.mallzhub.customer.util.DataBoundListAdapter

class OrderDetailsProductListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((SalesDetails) -> Unit)? = null
) : DataBoundListAdapter<SalesDetails, OrderDetailsProductListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<SalesDetails>() {
        override fun areItemsTheSame(oldItem: SalesDetails, newItem: SalesDetails): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: SalesDetails,
            newItem: SalesDetails
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): OrderDetailsProductListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_order_details_product, parent, false
        )
    }


    override fun bind(binding: OrderDetailsProductListItemBinding, position: Int) {
        val item = getItem(position)
        binding.item = item
        binding.imageUrl = item.product?.thumbnail
        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.thumbnail.setImageResource(R.drawable.image_placeholder)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}