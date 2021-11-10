package com.mallzhub.customer.ui.offer

import android.annotation.SuppressLint
import android.graphics.Paint
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
import com.mallzhub.customer.databinding.OfferProductListItemBinding
import com.mallzhub.customer.local_db.dbo.CartItem
import com.mallzhub.customer.models.OfferProductListResponseData

import com.mallzhub.customer.util.DataBoundListAdapter

class OfferProductListAdapter(
    private val appExecutors: AppExecutors,
    private val itemSelectionCallback: ((OfferProductListResponseData) -> Unit)
) : DataBoundListAdapter<OfferProductListResponseData, OfferProductListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<OfferProductListResponseData>() {
        override fun areItemsTheSame(oldItem: OfferProductListResponseData, newItem: OfferProductListResponseData): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: OfferProductListResponseData,
            newItem: OfferProductListResponseData
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): OfferProductListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_offer_product, parent, false
        )
    }

    override fun bind(binding: OfferProductListItemBinding, position: Int) {
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

        binding.root.setOnClickListener {
            itemSelectionCallback(item)
        }

        binding.mrp.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text = "${binding.root.context.getString(R.string.sign_taka)}120"
        }
    }
}